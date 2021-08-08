package com.generator;

import com.data.Record;

public interface BallGenerator {

    Record next();

    boolean hasNext();
}
