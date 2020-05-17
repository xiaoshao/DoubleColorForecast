package com.prepare.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by zwshao on 20/4/2018.
 */
public class RedisAccess {

    private Jedis client;

    public RedisAccess(String host, int port){
        this.client = new Jedis(host, port);
    }

    public void addData(int key, int no, int[] records) {
        StringBuilder data = new StringBuilder(records[0]);

        for(int index = 1; index < records.length; index ++){
            data.append(":").append(records[index]);
        }

        client.hset(String.valueOf(key), String.valueOf(no), data.toString());
    }

    public void addDatas(int key, Map<Integer, int[]> records){
        for(Map.Entry<Integer, int[]> recordEntry : records.entrySet()){
            addData(key, recordEntry.getKey(), recordEntry.getValue());
        }
    }

    public void addDatas(Map<Integer, Map<Integer, int[]>> records){
        for(Map.Entry<Integer, Map<Integer, int[]>> recordEntry : records.entrySet()){
            addDatas(recordEntry.getKey(), recordEntry.getValue());
        }
    }
}
