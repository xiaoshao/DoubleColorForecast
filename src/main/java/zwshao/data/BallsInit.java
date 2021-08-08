package zwshao.data;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class BallsInit {

    private String baseUrlPrefix1 = "http://kaijiang.zhcw.com/zhcw/inc/ssq/ssq_wqhg.jsp?pageNum=";
    //    private String baseUrlPrefix = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_";
//    private String baseUrlSuffix = ".html";
//    String homeUrl = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html";
    private String homeUrl = "http://kaijiang.zhcw.com/zhcw/inc/ssq/ssq_wqhg.jsp?pageNum=1";

    private int pageCount = 0;

    private List<Record> latestBalls = Lists.newArrayList();

    private List<Record> historyRecords;

    public BallsInit(DoubleColorPersistence doubleColorPersistence) {
        historyRecords = doubleColorPersistence.getAllRecords();
    }

    public List<Record> readNewBalls() throws Exception {
        String pageCountContent = getHtmlString(homeUrl);
        int totalCount = getTotalCount(pageCountContent);
        pageCount = getPageCount(pageCountContent);

        if (pageCount > 0 && totalCount > 0) {
            for (int startPage = 0; startPage <= pageCount; startPage++) {
                List<Record> recordsInSpecifiedPage = tryReadRecordsInSpecifiedPage(startPage);

                for (Record record : recordsInSpecifiedPage) {
                    if (!historyRecords.contains(record)) {
                        latestBalls.add(record);
                    }
                }

                if ((latestBalls.size() + historyRecords.size()) == totalCount) {
                    break;
                }
            }

            return latestBalls;
        } else {
            throw new Exception("the page count is less than 0 or total count is less than 0");
        }
    }

    private List<Record> tryReadRecordsInSpecifiedPage(int pageNo) throws Exception {
        for (int index = 0; index < 10; index++) {
            try {
                List<Record> records = readRecordsInSpecifiedPage(pageNo);

                System.out.println("finish to read page no " + pageNo);
                return records;
            } catch (Exception ex) {
                if (index < 9) {
                    Thread.sleep(2000);
                    continue;
                }

                throw ex;
            }
        }

        System.out.println("finish to read page no after tried 10 times " + pageNo);

        return null;
    }

    private List<Record> readRecordsInSpecifiedPage(int pageNo) throws Exception {
        String url = null;
//        if (pageNo >= 70) {
        url = baseUrlPrefix1 + pageNo;
//        } else {
//            url = baseUrlPrefix + pageNo + baseUrlSuffix;
//        }
        String pageContent = getHtmlString(url);
        if (pageContent != null && !pageContent.equals("")) {
            try {
                return parseRecords(pageContent);
            } catch (Exception ex) {
                throw new Exception("lost page " + pageNo);
            }
        } else {
            throw new Exception("lost page " + pageNo);
        }
    }

    public List<Record> getHistoryRecords() {
        return historyRecords;
    }

    private List<Record> parseRecords(String pageContent) throws Exception {
        String regex = "<td align=\"center\" style=\"padding-left:10px;\">[\\s\\S]+?</em></td>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);


        List<List<Integer>> pageBalls = Lists.newArrayList();
        while (matcher.find()) {
            String oneTermContent = matcher.group();
            pageBalls.add(parseBalls(oneTermContent));
        }

        String itemRegex = "<td align=\"center\">[\\d]{7}</td>";
        Pattern itemPattern = Pattern.compile(itemRegex);
        Matcher itemMatcher = itemPattern.matcher(pageContent);

        List<String> nos = Lists.newArrayList();
        while (itemMatcher.find()) {
            String no = itemMatcher.group();
            nos.add(no.substring(19, 26));
        }
        List<Record> balls = Lists.newArrayList();

        if (nos.size() != pageBalls.size()) {
            throw new Exception("failed to parse the balls");
        }

        for (int index = 0; index < nos.size(); index++) {
            String no = nos.get(index);
            List<Integer> recordBalls = pageBalls.get(index);

            Record record = new Record(no, recordBalls);

            balls.add(record);
        }
        return balls;
    }

    private List<Integer> parseBalls(String oneTermContent) {
        String regex = ">\\d+<";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oneTermContent);
        List<Integer> ball = Lists.newArrayList();
        while (matcher.find()) {
            String content = matcher.group();
            String ballNumber = content.substring(1, content.length() - 1);

            ball.add(Integer.parseInt(ballNumber));
        }

        return ball;
    }

    private String getHtmlString(String targetUrl) {
        String content = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.setRequestProperty("UA-CPU", "x86");
            //为什么没有deflate呢
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Content-type", "text/html");
            //keep-Alive，有什么用呢，你不是在访问网站，你是在采集。嘿嘿。减轻别人的压力，也是减轻自己。
            connection.setRequestProperty("Connection", "close");
            //不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
            connection.setUseCaches(false);
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (200 == connection.getResponseCode()) {
                InputStream inputStream = null;
                if (connection.getContentEncoding() != null && !connection.getContentEncoding().equals("")) {
                    String encode = connection.getContentEncoding().toLowerCase();
                    if (encode != null && !encode.equals("") && encode.indexOf("gzip") >= 0) {
                        inputStream = new GZIPInputStream(connection.getInputStream());
                    }
                }

                if (null == inputStream) {
                    inputStream = connection.getInputStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return content;
    }

    private int getPageCount(String pageContent) {
        String regex = "\\d+\">末页";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        String[] splits = null;
        while (matcher.find()) {
            String content = matcher.group();
            splits = content.split("\"");
            break;
        }
        if (splits != null && splits.length == 2) {
            String countString = splits[0];
            if (countString != null && !countString.equals("")) {
                return Integer.parseInt(countString);
            }

        }
        return 0;
    }

    private int getTotalCount(String pageContent) {
        String regex = "\\d+ </strong>条记录";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        String[] splits = null;
        while (matcher.find()) {
            String content = matcher.group();
            splits = content.split(" ");
            break;
        }

        if (splits != null && splits.length == 2) {
            String countString = splits[0];
            if (countString != null && !countString.equals("")) {
                return Integer.parseInt(countString);
            }

        }
        return 0;
    }

}
