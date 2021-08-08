package com.generator;

import com.data.Record;

public class BallGeneratorFactory {

    public static BallGenerator buildBallGenerator(Record startRecord) {
        return new DefaultBallGenerator(startRecord);
    }

    public static void main(String[] args) {
        BallGenerator ballGenerator = BallGeneratorFactory.buildBallGenerator(null);

        while (ballGenerator.hasNext()) {
            System.out.println(ballGenerator.next());
        }
    }
}
