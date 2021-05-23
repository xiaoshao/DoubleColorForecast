package com.algorithm;

import com.data.Record;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public abstract class BinaryAlgorithm implements Algorithm{

    private List<Record> records;

    public BinaryAlgorithm(List<Record> records) {
        this.records = records;
    }

    abstract double calculate(Record record, Record record1);


    public Map.Entry<Double, Double> calculate(List<Record> balls) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;
        for (Record ball : balls) {
            for(Record record : records){
                if(record.equals(ball)) {
                    continue;
                }
                double value = calculate(record, ball);

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
