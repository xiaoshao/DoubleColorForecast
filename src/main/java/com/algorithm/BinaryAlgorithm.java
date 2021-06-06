package com.algorithm;

import com.data.Record;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public abstract class BinaryAlgorithm implements Algorithm{

    private List<Record> historyRecords;

    public BinaryAlgorithm(List<Record> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public abstract double calculate(Record record, Record history);


    public Map.Entry<Double, Double> calculate(List<Record> balls) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;
        for (Record ball : balls) {
            for(Record history : historyRecords){
                if(history.equals(ball)) {
                    continue;
                }
                double value = calculate(history, ball);

                if(min > value) {
                    min = value;
                }

                if(max < value) {
                    max = value;
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }
}
