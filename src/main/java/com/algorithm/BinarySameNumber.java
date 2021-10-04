package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

import static com.algorithm.AlgorithmConst.BINARY_SAME_NUMBER;

public class BinarySameNumber extends BinaryAlgorithm {

    public BinarySameNumber(DoubleColorPersistence persistence, List<Record> newRecords) {
        super(persistence, newRecords);
    }

    @Override
    public double calculate(Record record, Record history) {
        List<Integer> balls = record.getRedBalls();
        List<Integer> otherBalls = history.getRedBalls();

        List<Integer> computeBalls = Lists.newArrayList(balls);
        computeBalls.retainAll(otherBalls);

        return computeBalls.size();
    }

    @Override
    public String getRestrictionName() {
        return BINARY_SAME_NUMBER;
    }

    @Override
    public boolean filterMap(Map.Entry<Integer, Integer> resultMapEntry) {
        return resultMapEntry.getKey() <= 4;
    }
}
