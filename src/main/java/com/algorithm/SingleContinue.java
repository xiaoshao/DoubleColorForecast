package com.algorithm;

import com.data.Record;

import java.util.List;

import static com.algorithm.AlgorithmConst.SINGLE_CONTINUE;

public class SingleContinue implements SingleAlgorithm {


    @Override
    public double calculate(Record record) {
        int continueNumber = 0;

        List<Integer> balls = record.getBalls();

        for (int index = 1; index < balls.size(); index++) {
            if (balls.get(index) - balls.get(index - 1) == 1) {
                continueNumber++;
            }
        }


        return continueNumber;
    }

    @Override
    public String getRestrictionName() {
        return SINGLE_CONTINUE;
    }
}
