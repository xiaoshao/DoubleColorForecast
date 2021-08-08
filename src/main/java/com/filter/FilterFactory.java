package com.filter;

import com.algorithm.*;
import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class FilterFactory {


    private static SingleFilter averageFilter(DoubleColorPersistence doubleColorPersistence) {
        SingleAverage singleAverage = new SingleAverage(doubleColorPersistence, null);

        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(singleAverage.getRestrictionName());

        SingleFilter singleFilter = new SingleFilter(singleAverage, restrictions.getKey(), restrictions.getValue());

        return singleFilter;
    }

    private static SingleFilter continueFilter(DoubleColorPersistence doubleColorPersistence) {
        SingleContinue singleContinue = new SingleContinue(doubleColorPersistence, null);

        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(singleContinue.getRestrictionName());

        SingleFilter singleFilter = new SingleFilter(singleContinue, restrictions.getKey(), restrictions.getValue());

        return singleFilter;
    }

    private static SingleFilter subFilter(DoubleColorPersistence doubleColorPersistence) {
        SingleSub singleSub = new SingleSub(doubleColorPersistence, null);
        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(singleSub.getRestrictionName());

        SingleFilter singleFilter = new SingleFilter(singleSub, restrictions.getKey(), restrictions.getValue());

        return singleFilter;
    }

    private static SingleFilter summaryFilter(DoubleColorPersistence doubleColorPersistence) {
        SingleSummary singleSummary = new SingleSummary(doubleColorPersistence, null);
        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(singleSummary.getRestrictionName());

        return new SingleFilter(singleSummary, restrictions.getKey(), restrictions.getValue());
    }

    private static BinaryFilter binaryAbsSub(DoubleColorPersistence doubleColorPersistence) {
        BinaryAbsSub binaryAbsSub = new BinaryAbsSub(doubleColorPersistence, null);
        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(binaryAbsSub.getRestrictionName());

        List<Record> historyRecords = doubleColorPersistence.getAllRecords();
        return new BinaryFilter(binaryAbsSub, historyRecords, restrictions.getKey(), restrictions.getValue());
    }

    private static BinaryFilter binarySameNumber(DoubleColorPersistence doubleColorPersistence) {
        BinarySameNumber binarySameNumber = new BinarySameNumber(doubleColorPersistence, null);
        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(binarySameNumber.getRestrictionName());

        List<Record> historyRecords = doubleColorPersistence.getAllRecords();
        return new BinaryFilter(binarySameNumber, historyRecords, restrictions.getKey(), restrictions.getValue());
    }

    private static BinaryFilter binarySub(DoubleColorPersistence doubleColorPersistence) {
        BinarySub binarySub = new BinarySub(doubleColorPersistence, null);
        Map.Entry<Double, Double> restrictions = doubleColorPersistence.readRestriction(binarySub.getRestrictionName());

        List<Record> historyRecords = doubleColorPersistence.getAllRecords();
        return new BinaryFilter(binarySub, historyRecords, restrictions.getKey(), restrictions.getValue());
    }

    public static BallFilter createFilter(DoubleColorPersistence doubleColorPersistence) {
        List<BallFilter> filters = Lists.newArrayList();

        filters.add(averageFilter(doubleColorPersistence));
        filters.add(continueFilter(doubleColorPersistence));
        filters.add(subFilter(doubleColorPersistence));
        filters.add(summaryFilter(doubleColorPersistence));

        filters.add(binaryAbsSub(doubleColorPersistence));
        filters.add(binarySameNumber(doubleColorPersistence));
        filters.add(binarySub(doubleColorPersistence));

        CompositeBallFilter compositeBallFilter = new CompositeBallFilter(filters);

        return compositeBallFilter;
    }
}
