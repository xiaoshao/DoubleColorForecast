package com.algorithm;

import com.data.Record;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface SingleAlgorithm extends Algorithm{
    double calculate(Record record);

    default Map.Entry<Double, Double> calculate(List<Record> balls) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;

        for (Record ball : balls) {
            double value = calculate(ball);

            if(min > value) {
                min = value;
            }

            if(max < value) {
                max = value;
            }
        }

        return new AbstractMap.SimpleEntry<>(min, max);
    }
}
