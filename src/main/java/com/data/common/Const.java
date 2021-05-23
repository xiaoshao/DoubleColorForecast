package com.data.common;

import com.data.Record;
import com.google.common.collect.Lists;

public class Const {

    public static final int MAX_BLUE = 15;
    public static final int MAX_RED = 31;

    public static final Record OVER_TAG = new Record("0", Lists.newArrayList(-1, -1, -1, -1, -1, -1), 1);
    public static final String SUMMARY_RANGE_KEY = "range|summary";
    public static final String SIMILAR_RANGE_KEY = "range|similar";
    public static final String AVG_RANGE_KEY = "range|avg";
    public static final String VARIANT_RANGE_KEY = "range|variant";
    public static final String CONTINUOUS_RANGE_KEY = "range|continuous";
    public static final String HISTORY_DATA_PREFIX = "history|";
    public static final String MAX_RECORD_KEY = "max|key";

}
