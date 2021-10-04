package com;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.data.persistence.DoubleColorPersistenceFactory;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateMaybeRecordMain {
    public static void main(String[] args) {
        DoubleColorPersistence persistence = DoubleColorPersistenceFactory.createPersistence();

        List<Record> maybeRecords = persistence.getAllMaybeRecords();

        Map<Integer, Integer> countMaps = summaryMaybeRecord(maybeRecords);

        List<Map.Entry<Integer, Integer>> countList = countMaps.entrySet().stream()
                .sorted((entry1, entry2) -> entry1.getValue() - entry2.getValue())
                .collect(Collectors.toList());

        List<Record> historyRecords = persistence.getAllRecords();

        Map<Integer, Integer> historyCountMaps = summaryMaybeRecord(historyRecords);

        List<Map.Entry<Integer, Integer>> historyList = historyCountMaps.entrySet().stream()
                .sorted((entry1, entry2) -> entry1.getValue() - entry2.getValue())
                .collect(Collectors.toList());


        outputSummary(countList, historyList);

        System.out.println("");
        System.out.println("history records order");

        for(int index = historyList.size() - 1; index >=0; index --) {
            System.out.print(", " + historyList.get(index).getKey());
        }
    }

    private static void outputSummary(List<Map.Entry<Integer, Integer>> countList, List<Map.Entry<Integer, Integer>> historyList) {
        for(int index = 0; index < Math.max(countList.size(), historyList.size()); index ++) {
            StringBuilder sb = new StringBuilder(100);
            sb.append(index + " : " );
            if(index < countList.size()){
                Integer maybeKey = countList.get(index).getKey();
                sb.append(" maybe : " + maybeKey);
            }

            if(index < historyList.size()) {
                Integer historyKey = historyList.get(index).getKey();
                sb.append(" history: " + historyKey);
            }

            System.out.println(sb.toString());
        }
    }

    @NotNull
    private static Map<Integer, Integer> summaryMaybeRecord(List<Record> maybeRecords) {
        Map<Integer, Integer> countMaps = Maps.newHashMap();

        maybeRecords.stream().flatMap(record -> record.getBalls().stream())
                .forEach(ball -> countMaps.compute(ball, (key, value) -> {
                    if (value == null) {
                        return 1;
                    } else {
                        return value + 1;
                    }
                }));
        return countMaps;
    }
}
