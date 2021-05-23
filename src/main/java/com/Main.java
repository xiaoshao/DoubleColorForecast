package com;

import com.algorithm.Algorithm;
import com.algorithm.AlgorithmFactory;
import com.algorithm.SingleAlgorithm;
import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.data.persistence.DoubleColorPersistenceFactory;
import com.google.common.collect.Maps;
import zwshao.data.BallsInit;

import java.util.List;
import java.util.Map;

/**
 * Created by zwshao on 6/4/2018.
 */
public class Main {


    public static void main(String[] args) throws Exception {
        DoubleColorPersistence persistence = DoubleColorPersistenceFactory.createPersistence();
        BallsInit ballsInit = new BallsInit(persistence);

        List<Record> balls = ballsInit.readAllBalls();

        persistence.saveRecords(balls);

        List<Algorithm> algorithms = AlgorithmFactory.createAlgorithm(balls);
        Map<String, Map.Entry<Double, Double>> restrictions = Maps.newHashMap();

        for (Algorithm algorithm : algorithms) {
            restrictions.put(algorithm.getRestrictionName(), algorithm.calculate(balls));
        }

        persistence.saveRestriction(restrictions);
    }
}
