package com.prepare.data;

import com.InitHistoryDataException;
import com.alibaba.fastjson.JSONObject;
import com.data.Record;
import okhttp3.*;

import java.util.*;

/**
 * Created by zwshao on 6/4/2018.
 */
public class ReadData {

    private String site = "http://sina.aicai.com/kaijiang/open/historyIssue.do";
    private int maxForYear = 100;


    public Map<Integer, Map<Integer, Record>> readAllData() {
        int startYear = 2003;
        int endYear = 2020;

        Map<Integer, Map<Integer, Record>> allData = new HashMap<>();

        for (Integer year = startYear; year <= endYear; year++) {
            allData.put(year, readDataByYear(year));
        }

        return allData;
    }

    public Map<Integer, Record> readDataByYear(int year) {
        Map<Integer, Record> records = new HashMap<>();
        for (int index = 1; index <= maxForYear; index++) {
            String no = formatNo(year, index);
            try {
                Optional<Record> recordOpt = getRecord(no);
                if (recordOpt.isPresent()) {
                    records.put(index, recordOpt.get());
                } else {
                    
                }
            } catch (InitHistoryDataException e) {
                e.printStackTrace();
            }

        }

        return records;
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

    private String formatNo(int year, int index) {
        String sub = "000" + index;
        return String.valueOf(year) + sub.substring(sub.length() - 3, sub.length());
    }
}
