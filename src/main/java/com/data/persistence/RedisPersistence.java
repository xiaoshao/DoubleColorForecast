package com.data.persistence;

import com.data.Record;
import com.google.common.base.Strings;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisPersistence implements DoubleColorPersistence {

    private Jedis jedis;
    private String DOUBLE_COLOR = "double_color";

    public RedisPersistence() {
        this.jedis = RedisConnectionPool.getConnection();
    }

    @Override
    public void saveRecord(Record record) {
        this.jedis.hset(DOUBLE_COLOR, record.getNo(), record.toString());
    }

    @Override
    public Record getRecord(String no) {
        String record = jedis.hget(DOUBLE_COLOR, no);

        if (Strings.isNullOrEmpty(record)) {
            return Record.parse(record);
        } else {
            return null;
        }
    }


    @Override
    public void saveRestriction(Map<String, Map.Entry<Double, Double>> restrictions) {
        for (String restrictionName : restrictions.keySet()) {
            jedis.hset("restriction", restrictionName,
                    restrictions.get(restrictionName).getKey() + " " + restrictions.get(restrictionName).getValue());
        }
    }
}
