package com.algorithm;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Algorithm {

    String getRestrictionName();

    Map.Entry<Double, Double> calculate();

    Map<Integer, Integer> getResultMap();


    default Map.Entry<Integer, Integer> getUpdatedRestriction() {
        Map<Integer, Integer> resultMap = getResultMap();

        List<Integer> keySet = resultMap.entrySet().stream()
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(Integer::valueOf))
                .collect(Collectors.toList());

        return new AbstractMap.SimpleEntry<>(keySet.get(0), keySet.get(keySet.size() - 1));
    }

    boolean filterMap(Map.Entry<Integer, Integer> resultMapEntry);

}

