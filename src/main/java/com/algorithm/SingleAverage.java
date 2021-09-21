package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;

import java.util.List;
import java.util.Map;

import static com.algorithm.AlgorithmConst.SINGLE_AVERAGE;

public class SingleAverage extends SingleAlgorithm {

    public SingleAverage(DoubleColorPersistence persistence, List<Record> newRecords) {
        super(persistence, newRecords);
    }

    @Override
    public double calculate(Record record) {
        int sum = 0;
        for (Integer ball : record.getBalls()) {
            sum += ball;
        }
        return sum / 7.0d;
    }

    @Override
    public String getRestrictionName() {
        return SINGLE_AVERAGE;
    }

    @Override
    public boolean filterMap(Map.Entry<Integer, Integer> resultMapEntry) {
        return resultMapEntry.getValue() > 50;
    }
}
