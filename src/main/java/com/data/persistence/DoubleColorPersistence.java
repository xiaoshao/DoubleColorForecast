package com.data.persistence;

import com.data.Record;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DoubleColorPersistence {

    default void saveRecords(Collection<? extends Record> records) {
        for (Record record : records) {
            saveRecord(record);
        }
    }

    void saveRecord(Record record);

    Record getRecord(String no);

    List<Record> getAllRecords();

    void saveRestriction(Map<String, Map.Entry<Double, Double>> restrictions);

    Map<String, Map.Entry<Double, Double>> readAllRestriction();

    Map.Entry<Double, Double> readRestriction(String key);

    void saveProcess(int pageNo);

    int getProcess();

    void saveMaybeRecord(Record record);

    List<Record> getAllMaybeRecords();

    Record getGeneratedLastRecord();

    void saveGeneratedLastRecord(Record record);
}
