package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;

import java.util.List;

import static com.algorithm.AlgorithmConst.SINGLE_SUB;

public class SingleSub extends SingleAlgorithm {

    public SingleSub(DoubleColorPersistence persistence, List<Record> newRecords) {
        super(persistence, newRecords);
    }

    @Override
    public double calculate(Record record) {
        List<Integer> redBalls = record.getRedBalls();

        int summary = 0;
        for (int index = 1; index < redBalls.size(); index++) {
            summary += Math.abs(redBalls.get(index) - redBalls.get(index - 1));
        }

        return summary;
    }

    @Override
    public String getRestrictionName() {
        return SINGLE_SUB;
    }
}
