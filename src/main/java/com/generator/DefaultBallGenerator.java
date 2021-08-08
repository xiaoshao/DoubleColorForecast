package com.generator;

import com.data.Record;
import com.google.common.collect.Lists;

public class DefaultBallGenerator implements BallGenerator {

    private final Record finalRecord = new Record("", Lists.newArrayList(26, 27, 28, 29, 30, 31), 15);

    private Record currentRecord;

    public DefaultBallGenerator() {

    }

    public DefaultBallGenerator(Record currentRecord) {
        this.currentRecord = currentRecord;
    }

    @Override
    public Record next() {

        if (currentRecord == null) {
            this.currentRecord = new Record("", Lists.newArrayList(1, 2, 3, 4, 5, 6), 1);
            return this.currentRecord;
        }

        Record next = this.currentRecord.next();
        this.currentRecord = next;
        return next;
    }

    @Override
    public boolean hasNext() {
        return !finalRecord.equals(currentRecord);
    }
}
