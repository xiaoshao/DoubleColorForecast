package com.algorithm;

import com.data.Record;

import java.util.List;

import static com.algorithm.AlgorithmConst.BINARY_SUB;

public class BinarySub extends BinaryAlgorithm {

    public BinarySub(List<Record> records) {
        super(records);
    }

    @Override
    public double calculate(Record record, Record history) {
        List<Integer> balls = record.getBalls();
        List<Integer> otherBalls = history.getBalls();

        double summary = 0;
        for (int index = 0; index < balls.size(); index++) {
            summary += (balls.get(index) - otherBalls.get(index));
        }

        return summary;
    }


    @Override
    public String getRestrictionName() {
        return BINARY_SUB;
    }
}
