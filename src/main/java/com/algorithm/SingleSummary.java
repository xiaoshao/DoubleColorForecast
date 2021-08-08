package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;

import java.util.List;

import static com.algorithm.AlgorithmConst.SINGLE_SUMMERY;

public class SingleSummary extends SingleAlgorithm {

    public SingleSummary(DoubleColorPersistence persistence, List<Record> newRecords) {
        super(persistence, newRecords);
    }

    @Override
    public double calculate(Record record) {
        List<Integer> balls = record.getBalls();

        double summary = 0;

        for (Integer ball : balls) {
            summary += ball;
        }

        return summary;
    }

    @Override
    public String getRestrictionName() {
        return SINGLE_SUMMERY;
    }
}
