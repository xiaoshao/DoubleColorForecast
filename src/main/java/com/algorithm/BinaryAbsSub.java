package com.algorithm;

import com.data.Record;

import java.util.List;

import static com.algorithm.AlgorithmConst.BINARY_ABS_SUB;

public class BinaryAbsSub extends BinaryAlgorithm {

    public BinaryAbsSub(List<Record> records) {
        super(records);
    }

    @Override
    double calculate(Record record, Record otherRecord) {
        List<Integer> balls = record.getBalls();
        List<Integer> otherBalls = otherRecord.getBalls();

        double summary = 0;

        for (int index = 0; index < balls.size(); index++) {
            summary += Math.abs(balls.get(index) - otherBalls.get(index));
        }

        return summary;
    }

    @Override
    public String getRestrictionName() {
        return BINARY_ABS_SUB;
    }
}
