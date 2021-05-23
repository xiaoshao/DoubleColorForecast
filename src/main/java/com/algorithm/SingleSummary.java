package com.algorithm;

import com.data.Record;

import java.util.List;

import static com.algorithm.AlgorithmConst.SINGLE_SUMMERY;

public class SingleSummary implements SingleAlgorithm {

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
