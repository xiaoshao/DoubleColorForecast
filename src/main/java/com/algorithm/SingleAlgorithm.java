package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SingleAlgorithm implements Algorithm {
    List<Record> newRecords;
    Map.Entry<Double, Double> restriction;
    List<Record> allRecords;

    List<Record> maxRecords;

    List<Record> minRecords;

    private Map<Integer, Integer> maps = Maps.newHashMap();

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

            maps.compute((int)(Math.floor(value)), (key, value1)-> {if(value1 == null) {return 1;}else {return value1 + 1;}});

            if (min > value) {
                min = value;
                minRecords = Lists.newArrayList(ball);
            }

            if (max < value) {
                max = value;
                maxRecords = Lists.newArrayList(ball);
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }

    @Override
    public Map<Integer, Integer> getResultMap() {
        return maps.entrySet().stream().filter(this::filterMap).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
