package com.data.persistence;

import com.data.Record;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import redis.clients.jedis.Jedis;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisPersistence implements DoubleColorPersistence {

    private Jedis jedis;

    private String DOUBLE_COLOR = "double_color";

    private String PROCESS_KEY = "process_key";

    private String GENERATED_LAST_RECORD = "generated_last_record";

    private String MAY_BE_RECORD = "may_be_record";

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

        if (!Strings.isNullOrEmpty(record)) {
            return Record.parse(record);
        } else {
            return null;
        }
    }

    @Override
    public List<Record> getAllRecords() {

        Set<String> keys = jedis.hkeys(DOUBLE_COLOR);

        List<Record> records = Lists.newArrayList();
        for (String key : keys) {
            records.add(getRecord(key));
        }

        return records;
    }


    @Override
    public void saveRestriction(Map<String, Map.Entry<Double, Double>> restrictions) {
        for (String restrictionName : restrictions.keySet()) {
            jedis.hset("restriction", restrictionName,
                    restrictions.get(restrictionName).getKey() + " " + restrictions.get(restrictionName).getValue());
        }
    }

    @Override
    public Map<String, Map.Entry<Double, Double>> readAllRestriction() {

        if (jedis.exists("restriction")) {
            Set<String> restrictionKeys = jedis.hkeys("restriction");

            Map<String, Map.Entry<Double, Double>> restrictions = Maps.newHashMap();
            for (String restrictionKey : restrictionKeys) {
                Map.Entry<Double, Double> restriction = readRestriction(restrictionKey);
                restrictions.put(restrictionKey, restriction);
            }

            return restrictions;
        } else {
            return Maps.newHashMap();
        }


    }

    @Override
    public Map.Entry<Double, Double> readRestriction(String key) {
        if (jedis.hexists("restriction", key)) {
            String restrictionValue = jedis.hget("restriction", key);

            String[] values = restrictionValue.split(" ");

            if (values.length != 2) {
                jedis.hdel("restriction", key);
                return null;
            }

            double value1 = Double.parseDouble(values[0]);
            double value2 = Double.parseDouble(values[1]);

            double min = Math.min(value1, value2);
            double max = Math.max(value1, value2);

            return new AbstractMap.SimpleEntry<>(min, max);
        }

        return null;
    }

    @Override
    public void saveProcess(int pageNo) {
        jedis.set(PROCESS_KEY, String.valueOf(pageNo));
    }

    @Override
    public int getProcess() {
        if (jedis.exists(PROCESS_KEY)) {
            return Integer.parseInt(jedis.get(PROCESS_KEY));
        }
        return 0;
    }

    @Override
    public void saveMaybeRecord(Record record) {
        jedis.sadd(MAY_BE_RECORD, record.toString());
    }

    @Override
    public List<Record> getAllMaybeRecords() {
        Set<String> allMaybeRecords = jedis.smembers(MAY_BE_RECORD);

        if(allMaybeRecords == null){
            return Lists.newArrayList();
        }

        return allMaybeRecords.stream().map(Record::parse).collect(Collectors.toList());
    }

    @Override
    public Record getGeneratedLastRecord() {
        if (jedis.exists(GENERATED_LAST_RECORD)) {
            return Record.parse(jedis.get(GENERATED_LAST_RECORD));
        }

        return null;
    }

    @Override
    public void saveGeneratedLastRecord(Record record) {
        jedis.set(GENERATED_LAST_RECORD, record.toString());
    }
}
