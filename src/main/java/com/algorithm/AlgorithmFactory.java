package com.algorithm;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.google.common.collect.Lists;

import java.util.List;

public class AlgorithmFactory {

    public static List<BinaryAlgorithm> createBinaryAlgorithm(DoubleColorPersistence persistence, List<Record> newRecords) {
        List<BinaryAlgorithm> binaryAlgorithms = Lists.newArrayList();

        binaryAlgorithms.add(new BinaryAbsSub(persistence, newRecords));
        binaryAlgorithms.add(new BinarySub(persistence, newRecords));
        binaryAlgorithms.add(new BinarySameNumber(persistence, newRecords));

        return binaryAlgorithms;
    }

    public static List<SingleAlgorithm> createSingleAlgorithm(DoubleColorPersistence persistence, List<Record> newRecords) {
        List<SingleAlgorithm> algorithms = Lists.newArrayList();

        algorithms.add(new SingleAverage(persistence, newRecords));
        algorithms.add(new SingleSummary(persistence, newRecords));
        algorithms.add(new SingleSub(persistence, newRecords));
        algorithms.add(new SingleContinue(persistence, newRecords));

        return algorithms;
    }

    public static List<Algorithm> createAlgorithm(DoubleColorPersistence persistence, List<Record> newRecords) {
        List<Algorithm> algorithms = Lists.newArrayList();

        algorithms.addAll(createBinaryAlgorithm(persistence, newRecords));
        algorithms.addAll(createSingleAlgorithm(persistence, newRecords));

        return algorithms;
    }
}
