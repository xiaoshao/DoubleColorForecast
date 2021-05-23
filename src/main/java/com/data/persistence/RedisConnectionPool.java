package com.data.persistence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionPool {

    private static JedisPool pool;
    private static JedisPoolConfig config = new JedisPoolConfig();

    static {
        config.setBlockWhenExhausted(true);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        config.setMaxIdle(20);
        config.setMaxWaitMillis(200);
        config.setMinEvictableIdleTimeMillis(1800000);
        config.setMinIdle(0);
        config.setTestOnBorrow(true);
        config.setMaxTotal(300);
        pool = new JedisPool(config, "localhost", 6379);
    }


    public static Jedis getConnection() {
        return pool.getResource();
    }

}
