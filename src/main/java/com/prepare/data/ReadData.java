package com.prepare.data;

import com.InitHistoryDataException;
import com.alibaba.fastjson.JSONObject;
import com.data.Record;
import com.data.common.Const;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zwshao on 6/4/2018.
 */
public class ReadData {

    private Logger logger = LoggerFactory.getLogger(ReadData.class);
    private String site = "http://sina.aicai.com/kaijiang/open/historyIssue.do";
    private int maxForYear = 100;
    private String year;
    private Jedis jedis;
    private String errorKey;
    private String storageKey;
    private int startNo = -1;

    public ReadData(Jedis jedis, String year) {
        this.year = year;
        this.jedis = jedis;
        this.errorKey = Const.HISTORY_DATA_PREFIX + "error|" + year;
        this.storageKey = Const.HISTORY_DATA_PREFIX + year;
    }

    public ReadData(Jedis jedis, String year, int startNo) {
        this(jedis, year);
        if (startNo >= 1) {
            this.startNo = startNo;
        }
    }

    public boolean isInited() {
        return jedis.exists(storageKey);
    }

    public boolean isErrorExists() {
        return jedis.exists(this.errorKey);
    }

    public void initData() {
        if (!isInited()) {
            readHistoryDataByYear();
        }

        if (isErrorExists()) {
            loadErrorRecordFromInternet();
        }
    }

    public void readHistoryDataByYear() {
        List<Record> records = Lists.newArrayList();

        for (int index = this.startNo; index < maxForYear; index++) {
            String no = formatNo(index);
            try {
                Optional<Record> recordOpt = getRecord(no);
                if (recordOpt.isPresent()) {
                    records.add(recordOpt.get());
                } else {
                    break;
                }
            } catch (InitHistoryDataException e) {
                jedis.sadd(errorKey, no);
                logger.error("init history data error: " + no);
            }
            System.out.println("read data " + no);
        }

        Map<String, String> datas = records.parallelStream().collect(Collectors.toMap(Record::getNo, Record::toString));

        jedis.hset(storageKey, datas);


    }

    private Optional<Record> getRecord(String no) throws InitHistoryDataException {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            RequestBody formBody = new FormBody.Builder()
                    .add("gameIndex", "101")
                    .add("issueNo", no)
                    .build();
            Request request = builder.url(site).post(formBody).build();
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                return Optional.of(parseRecord(no, response.body().string()));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new InitHistoryDataException("request Exception");
        }
    }


    private Record parseRecord(String issueNo, String responseData) {
        JSONObject obj = JSONObject.parseObject(responseData);
        String openResult = obj.getString("openResult");
        return parseOpenResult(issueNo, openResult);
    }

    private Record parseOpenResult(String issueNo, String openResult) {
        //"<i>18</i><i>19</i><i>21</i><i>26</i><i>27</i><i>33</i><i class='blue'>16</i>"

        String splitter = "<i>";
        String splitter1 = "</i>";
        String splitter2 = "<i class='blue'>";
        String data = openResult.replaceAll(splitter, "");
        data = data.replaceAll(splitter2, "");
        data = data.replaceAll(splitter1, ",");
        List<Integer> reds = new ArrayList<>();
        String[] res = data.split(",");

        reds.add(Integer.parseInt(res[0]));
        reds.add(Integer.parseInt(res[1]));
        reds.add(Integer.parseInt(res[2]));
        reds.add(Integer.parseInt(res[3]));
        reds.add(Integer.parseInt(res[4]));
        reds.add(Integer.parseInt(res[5]));

        return new Record(issueNo, reds, Integer.parseInt(res[6]));
    }

    private String formatNo(int index) {
        String sub = "000" + index;
        return year + sub.substring(sub.length() - 3, sub.length());
    }
}
