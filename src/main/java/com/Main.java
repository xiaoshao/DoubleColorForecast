package com;

import com.prepare.data.ReadData;
import com.prepare.redis.RedisAccess;

import java.util.Map;

/**
 * Created by zwshao on 6/4/2018.
 */
public class Main {

    public static String host = "";
    public static int port = 8080;

    public static void main(String[] args){

        ReadData readData = new ReadData();

//        readData.getRecord("2003001");
//        Map<Integer, Map<Integer, int[]>> allDatas = readData.readAllData();
//
//        RedisAccess redisAccess = new RedisAccess(host, port);
//
//        redisAccess.addDatas(allDatas);
    }
}
