package com.data.persistence;

public class DoubleColorPersistenceFactory {

    public static DoubleColorPersistence createPersistence() {
        return new RedisPersistence();
    }
}
