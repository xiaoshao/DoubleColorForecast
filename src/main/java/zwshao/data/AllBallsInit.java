package zwshao.data;

import com.data.Record;
import com.google.common.collect.Lists;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class AllBallsInit {


    private static StringBuffer mStringBuffer;

    public static List<Record> allBalls = Lists.newArrayList();

    public static void main(String[] args) {
        System.out.println("正在获取...");
        mStringBuffer = new StringBuffer();

        String baseUrlPrefix = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_";
        String baseUrlSuffix = ".html";
        String homeUrl = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html";
        String pageCountContent = getHtmlString(homeUrl);
        int pageCount = getPageCount(pageCountContent);
        if (pageCount > 0) {
            for (int i = 1; i <= pageCount; i++) {
                String url = baseUrlPrefix + i + baseUrlSuffix;
                String pageContent = getHtmlString(url);
                if (pageContent != null && !pageContent.equals("")) {
                    getOneTermContent(pageContent);

                } else {
                    System.out.println("第" + i + "页丢失");
                }

                try {
                    Thread.sleep(1200);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            File file = new File("doubleColor.txt");
            if (file.exists()) {
                file.delete();
            }

            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(mStringBuffer.toString());
                bufferedWriter.close();
                writer.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //BufferedWriter writer = new BufferedWriter(new OutputS)

        } else {
            System.out.println("结果页数为0");
        }

        System.out.println("完成！");
    }


    /**
     * 获取总页数 * @param result
     */
    private static int getPageCount(String result) {
        String regex = "\\d+\">末页";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
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

    /**
     * 获取网页源码 * @return
     */
    private static String getHtmlString(String targetUrl) {
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

    private static void getOneTermContent(String pageContent) {
        String regex = "<td align=\"center\" style=\"padding-left:10px;\">[\\s\\S]+?</em></td>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        while (matcher.find()) {
            String oneTermContent = matcher.group();
            getOneTermNumbers(oneTermContent);
        }
    }

    private static void getOneTermNumbers(String oneTermContent) {

        String regex = ">\\d+<";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oneTermContent);
        while (matcher.find()) {
            String content = matcher.group();
            String ballNumber = content.substring(1, content.length() - 1);
            mStringBuffer.append(ballNumber).append(" ");
        }

        mStringBuffer.append("\r\n");
    }
}
