package com.algorithm;

import com.data.Record;

import java.util.List;
import java.util.Map;

public interface Algorithm {

    String getRestrictionName();

    Map.Entry<Double,Double> calculate(List<Record> balls);
}

