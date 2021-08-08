package com;

import com.algorithm.Algorithm;
import com.algorithm.AlgorithmFactory;
import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.data.persistence.DoubleColorPersistenceFactory;
import com.filter.BallFilter;
import com.filter.FilterFactory;
import com.generator.BallGenerator;
import com.generator.BallGeneratorFactory;
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
        boolean readBallFailedFlag = false;

        List<Record> newBalls = null;
        try {
            newBalls = ballsInit.readNewBalls();
        } catch (Exception ex) {
            readBallFailedFlag = true;
        }

        persistence.saveRecords(newBalls);

        computeRestriction(persistence, newBalls);

        if (readBallFailedFlag) {
            throw new Exception("failed to read records");
        }

        Record startRecord = persistence.getGeneratedLastRecord();
        BallGenerator ballGenerator = BallGeneratorFactory.buildBallGenerator(startRecord);

        BallFilter ballFilter = FilterFactory.createFilter(persistence);
        while (ballGenerator.hasNext()) {
            Record record = ballGenerator.next();

            if (!ballFilter.filter(record)) {
                persistence.saveMaybeRecord(record);
            }

            persistence.saveGeneratedLastRecord(record);
        }

        List<Record> maybeRecords = persistence.getAllMaybeRecords();

        System.out.println("output all maybe record");
        for (Record maybeRecord : maybeRecords) {
            System.out.println(maybeRecord.toString());
        }

    }


    private static void computeRestriction(DoubleColorPersistence persistence, List<Record> newRecords) {
        List<Algorithm> algorithms = AlgorithmFactory.createAlgorithm(persistence, newRecords);
        Map<String, Map.Entry<Double, Double>> restrictions = Maps.newHashMap();

        for (Algorithm algorithm : algorithms) {
            Map.Entry<Double, Double> restriction = algorithm.calculate();
            String restrictionName = algorithm.getRestrictionName();
            System.out.println("calculated " + restrictionName + " value ( " + restriction.getKey() + ", " + restriction.getValue() + ")");
            restrictions.put(restrictionName, restriction);
        }

        persistence.saveRestriction(restrictions);
    }


}
