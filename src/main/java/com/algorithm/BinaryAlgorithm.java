package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BinaryAlgorithm implements Algorithm {

    private List<Record> allRecords;
    private List<Record> newRecords;
    private Map.Entry<Double, Double> restriction;

    private List<Record> minRecords;
    private List<Record> maxRecords;

    private Map<Integer, Integer> maps = Maps.newHashMap();

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

                maps.compute((int) value, (key, value1) -> {
                    if (value1 == null) {
                        return 1;
                    } else {
                        return value1 + 1;
                    }
                });


                if (min > value) {
                    min = value;
                    minRecords = Lists.newArrayList(ball, history);
                }

                if (max < value) {
                    max = value;
                    maxRecords = Lists.newArrayList(ball, history);
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }

    @Override
    public Map<Integer, Integer> getResultMap() {
        return maps.entrySet().stream().filter(this::filterMap).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
