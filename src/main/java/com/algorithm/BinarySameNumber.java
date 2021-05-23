package com.algorithm;

import com.data.Record;

import java.util.List;

import static com.algorithm.AlgorithmConst.BINARY_SAME_NUMBER;

public class BinarySameNumber extends BinaryAlgorithm {



    public BinarySameNumber(List<Record> records) {
        super(records);
    }

    @Override
    double calculate(Record record, Record otherRecord) {
        List<Integer> balls = record.getBalls();
        List<Integer> otherBalls = otherRecord.getBalls();

        balls.retainAll(otherBalls);

        return balls.size();
    }

    @Override
    public String getRestrictionName() {
        return BINARY_SAME_NUMBER;
    }
}
