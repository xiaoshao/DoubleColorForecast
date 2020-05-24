package com.data;

import com.google.common.base.Objects;

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
}
