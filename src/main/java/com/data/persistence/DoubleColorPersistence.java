package com.data.persistence;

import com.data.Record;

import java.util.Collection;
import java.util.Map;

public interface DoubleColorPersistence {

    default void saveRecords(Collection<? extends Record> records) {
        for (Record record : records) {
            saveRecord(record);
        }
    }

    void saveRecord(Record record);

    Record getRecord(String no);

    void saveRestriction(Map<String, Map.Entry<Double, Double>> restrictions);
}
