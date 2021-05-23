package com.algorithm;

import com.data.Record;
import com.google.common.collect.Lists;

import java.util.List;

public class AlgorithmFactory {

    public static List<BinaryAlgorithm> createBinaryAlgorithm(List<Record> records) {
        List<BinaryAlgorithm> binaryAlgorithms = Lists.newArrayList();

        binaryAlgorithms.add(new BinaryAbsSub(records));
        binaryAlgorithms.add(new BinarySub(records));

        return binaryAlgorithms;
    }

    public static List<SingleAlgorithm> createSingleAlgorithm() {
        List<SingleAlgorithm> algorithms = Lists.newArrayList();

        algorithms.add(new SingleAverage());
        algorithms.add(new SingleSummary());
        algorithms.add(new SingleSub());
        algorithms.add(new SingleContinue());

        return algorithms;
    }

    public static List<Algorithm> createAlgorithm(List<Record> records) {
        List<Algorithm> algorithms = Lists.newArrayList();

        algorithms.addAll(createBinaryAlgorithm(records));
        algorithms.addAll(createSingleAlgorithm());

        return algorithms;
    }
}
