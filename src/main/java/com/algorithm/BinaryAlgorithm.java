package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public abstract class BinaryAlgorithm implements Algorithm {

    private List<Record> allRecords;
    private List<Record> newRecords;
    private Map.Entry<Double, Double> restriction;

    public BinaryAlgorithm(DoubleColorPersistence persistence, List<Record> newRecords) {
        this.allRecords = persistence.getAllRecords();
        this.newRecords = newRecords;
        this.restriction = persistence.readRestriction(getRestrictionName());
    }

    public abstract double calculate(Record record, Record history);


    public Map.Entry<Double, Double> calculate() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        if ((newRecords == null || newRecords.size() == 0) && restriction != null) {
            return restriction;
        }

        List<Record> target;
        if (restriction != null) {
            min = Math.min(restriction.getKey(), restriction.getValue());
            max = Math.max(restriction.getKey(), restriction.getValue());
            target = newRecords;
        } else {
            target = allRecords;
        }

        for (Record ball : target) {
            for (Record history : allRecords) {
                if (history.equals(ball)) {
                    continue;
                }
                double value = calculate(ball, history);

                if (min > value) {
                    min = value;
                }

                if (max < value) {
                    max = value;
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }
}
