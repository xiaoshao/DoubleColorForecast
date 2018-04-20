package com.prepare.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by zwshao on 6/4/2018.
 */
public class ReadData {

    String site = "http://www.17500.cn/ssq/details.php?issue=";
    int maxForYear = 200;

    public Map<Integer, List<int[]>> readAllData(){
        int startYear = 2003;
        int endYear = 2018;

        Map<Integer, List<int[]>> allData = new HashMap<>();

        for(int year = startYear; year <= endYear; year ++){
            allData.put(year, readDataByYear(year));
        }
        return allData;
    }

    public List<int[]> readDataByYear(int year){
        List<int[]> records = new ArrayList<>();

        for(int index = 1; index <= maxForYear; index ++){
            String no = formatNo(year, index);
            int[] record = getRecord(no);
            if(record != null){
                records.add(record);
            }
        }

        return records;
    }

    private int[] getRecord(String no) {

        try {

            Document doc = Jsoup.connect(site + no).get();
            Elements elements = doc.select("tr[bgcolor='#ffffff']>td>font");
            if(elements.size() == 0){
                return null;
            }
            int[] nos = new int[7];
            int index = 0;
            Iterator<Element> it = elements.iterator();
            while (it.hasNext()){
                String text = it.next().text();

                nos[index ++] = Integer.valueOf(text);
            }

            return nos;

        } catch (Exception e) {
            return null;
        }

    }

    private String formatNo(int year, int index) {
        String sub = "000" + index;


        return String.valueOf(year) + sub.substring(sub.length() - 3, sub.length());
    }
}