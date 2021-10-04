package cal;

import com.data.Record;
import com.data.persistence.DoubleColorPersistence;
import com.data.persistence.DoubleColorPersistenceFactory;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    static DoubleColorPersistence persistence = DoubleColorPersistenceFactory.createPersistence();
    static List<Record> historyRecords = persistence.getAllRecords();
    static List<Record> maybeRecords = persistence.getAllMaybeRecords();
    static int count = 7;

    public static void main(String[] args) {

        //order by number

        Integer[] historyCount = new Integer[]{1, 14, 7, 6, 9, 12, 16, 11, 8, 2, 3, 10, 13, 5, 4, 15, 26, 22, 32, 20, 18, 17, 27, 19, 30, 25, 29, 23, 21, 31, 24, 28, 33};

        Integer[] originRecord = Arrays.copyOfRange(historyCount, 0, 6);

        Integer[] replaceRange = Arrays.copyOfRange(historyCount, 6, 10);

        List<Integer[]> originRecords = Lists.newArrayList();

        originRecords.add(originRecord);

        List<Integer[]> redBs = generateRedBs(originRecords, replaceRange);

        for (Integer[] redB : redBs) {
//            Integer[] redB = new Integer[]{1, 6, 7, 9, 12, 14};

            List<Integer> redBall = Arrays.asList(redB);

            Collections.sort(redBall);

            Record possible = isPossible(redBall);

            if (possible != null) {
                System.out.println("the possible record " + possible);
            }
        }
    }

    private static List<Integer[]> generateRedBs(List<Integer[]> originRecords, Integer[] possibleBalls) {
        if (possibleBalls == null || possibleBalls.length == 0) {
            return originRecords;
        }


        Integer[] poBalls = possibleBalls;

        while (poBalls.length != 0) {
            int nextBall = poBalls[0];

            poBalls = Arrays.copyOfRange(poBalls, 1, poBalls.length);
            List<Integer[]> records = Lists.newArrayList();

            for (Integer[] originRecord : originRecords) {
                for (int index = 0; index < 6; index++) {
                    Integer[] nextBalls = replace(originRecord, index, nextBall);
                    records.add(nextBalls);
                }
            }

            originRecords.addAll(records);
        }

        return originRecords;
    }

    private static Integer[] replace(Integer[] generatedRedBall, int replaceIndex, int replaceBalls) {
        Integer[] next = new Integer[6];
        for (int index = 0; index < 6; index++) {
            if (index == replaceIndex) {
                next[index] = replaceBalls;
            } else {
                next[index] = generatedRedBall[index];
            }
        }
        return next;
    }

    private static Record isPossible(List<Integer> redBall) {
        for (int blue = 1; blue <= 15; blue++) { // 3
            List<Integer> recordNumber = Lists.newArrayList();
            recordNumber.addAll(redBall);
            recordNumber.add(blue);

            Record record = new Record(null, redBall, blue);

            boolean maybeStatus = maybeRecords.contains(record);

            boolean historyStatus = historyRecords.contains(record);

            if (!historyStatus && maybeStatus) {
                return record;
            }
        }

        return null;
    }
}
