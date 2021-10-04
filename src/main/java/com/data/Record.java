package com.data;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Record {

    private String no;
    private List<Integer> redBalls;
    private Integer blueBalls;

    public Record(String no, List<Integer> redBalls, Integer blueBalls) {
        this.no = no;
        this.redBalls = redBalls;
        this.blueBalls = blueBalls;
    }

    public Record(String no, List<Integer> balls) {
        this.no = no;
        this.redBalls = balls.subList(0, 6);
        this.blueBalls = balls.get(6);
    }

    public List<Integer> getBalls() {
        List<Integer> balls = Lists.newArrayList();

        balls.addAll(redBalls);
        balls.add(blueBalls);

        return balls;
    }

    public List<Integer> getRedBalls() {
        return redBalls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(no).append("-");
        for (Integer redBall : redBalls) {
            sb.append(redBall).append(",");
        }
        sb.append(blueBalls);
        return sb.toString();
    }

    public static Record parse(String record) {
        String[] data = record.split("-");
        String[] balls = data[1].split(",");

        List<Integer> redBalls = new ArrayList<>();
        redBalls.add(Integer.parseInt(balls[0]));
        redBalls.add(Integer.parseInt(balls[1]));
        redBalls.add(Integer.parseInt(balls[2]));
        redBalls.add(Integer.parseInt(balls[3]));
        redBalls.add(Integer.parseInt(balls[4]));
        redBalls.add(Integer.parseInt(balls[5]));

        return new Record(data[0], redBalls, Integer.parseInt(balls[6]));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(redBalls, blueBalls);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Record other = (Record) obj;


        return Objects.equal(this.redBalls, other.redBalls)
                && Objects.equal(this.blueBalls, other.blueBalls);
    }


    public String getNo() {
        return no;
    }

    public Record next() {
        if (blueBalls < 15) {
            List<Integer> redBalls = Lists.newArrayList();
            redBalls.addAll(this.redBalls);
            return new Record("", redBalls, blueBalls + 1);
        } else {

            List<Integer> redBalls = Lists.newArrayList();
            redBalls.addAll(this.redBalls);

            for (int index = 5; index >= 0; index--) {
                if (redBalls.get(index) == (31 - (5 - index))) {
                    redBalls.set(index, redBalls.get(index - 1) + 1);
                } else {
                    redBalls.set(index, redBalls.get(index) + 1);
                    for (int innerIndex = index + 1; innerIndex <= 5; innerIndex++) {
                        redBalls.set(innerIndex, redBalls.get(innerIndex - 1) + 1);
                    }

                    break;
                }
            }

            return new Record("", redBalls, 1);
        }
    }
}
