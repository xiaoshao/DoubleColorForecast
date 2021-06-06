package com.filter;


import com.algorithm.SingleAlgorithm;
import com.data.Record;

public class SingleFilter implements BallFilter {


    private SingleAlgorithm algorithm;
    private double min;
    private double max;

    public SingleFilter(SingleAlgorithm algorithm, double min, double max) {
        this.algorithm = algorithm;
        this.max = max;
        this.min = min;
    }

    @Override
    public boolean filter(Record record) {
        double value = algorithm.calculate(record);

        if (value >= min && value <= max) {
            return false;
        } else {
            return true;
        }
    }
}
