package com.filter;


import com.algorithm.BinaryAlgorithm;
import com.data.Record;

import java.util.List;

public class BinaryFilter implements BallFilter {

    private BinaryAlgorithm algorithm;
    private List<Record> historyRecords;
    private double max;
    private double min;

    public BinaryFilter(BinaryAlgorithm algorithm, List<Record> historyRecords, double min, double max) {
        this.algorithm = algorithm;
        this.historyRecords = historyRecords;
        this.max = max;
        this.min = min;
    }

    @Override
    public boolean filter(final Record record) {
        for (Record historyRecord : historyRecords) {

            double value = algorithm.calculate(record, historyRecord);

            if (value < min || value > max) {
                return true;
            }
        }
        
        return false;
    }
}
