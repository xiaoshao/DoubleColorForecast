package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public abstract class SingleAlgorithm implements Algorithm {
    List<Record> newRecords;
    Map.Entry<Double, Double> restriction;
    List<Record> allRecords;

    public SingleAlgorithm(DoubleColorPersistence persistence, List<Record> newRecords) {
        this.newRecords = newRecords;
        this.allRecords = persistence.getAllRecords();
        this.restriction = persistence.readRestriction(getRestrictionName());
    }

    public abstract double calculate(Record record);

    public Map.Entry<Double, Double> calculate() {
        if ((newRecords == null || newRecords.size() == 0) && restriction != null) {
            return restriction;
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        List<Record> target;

        if (restriction == null) {
            target = allRecords;
        } else {
            target = newRecords;
            min = Math.min(restriction.getKey(), restriction.getValue());
            max = Math.max(restriction.getKey(), restriction.getValue());
        }
        for (Record ball : target) {
            double value = calculate(ball);

            if (min > value) {
                min = value;
            }

            if (max < value) {
                max = value;
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }
}
