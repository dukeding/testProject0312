package main;

import pojo.MySourceClass;
import pojo.MySourceRecord;
import pojo.MyTargetClass;
import utils.StreamUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class Test0312 {
    public static void main(String[] args) {
        var count1 = 500000; // source list size
        var count2 = 10; // execution times
        boolean verbose1 = false; // print target obj
        boolean verbose2 = false; // print duration

        var sourceList1 = Stream.generate(() -> new MySourceClass(
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7"
            )).limit(count1).toList();
        var sourceList2 = Stream.generate(() -> new MySourceRecord(
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7",
                123, 456L, 78.9, "String1", "String2", "String3", "String4", "String5", "String6", "String7"
            )).limit(count1).toList();

        long start, end, duration;

        long[] times = new long[count2];

        for (var j = 0; j < 5; j++) {
            for (int i = 0; i < count2; i++) {
                start = System.currentTimeMillis();
                var targetList1 = StreamUtils.transformList1(sourceList1, MySourceClass.class, MyTargetClass.class);
                end = System.currentTimeMillis();
                duration = end - start;

                if (verbose2) {
                    System.out.println(i + ": duration = " + duration);
                }

                times[i] = duration;

                if (verbose1) {
                    targetList1.forEach(System.out::println);
                }
            }

            System.out.println("Iteration " + j + ", Method 1: Max = " + Arrays.stream(times).max() + ", Min = " + Arrays.stream(times).min() + ", Avg = " + Arrays.stream(times).average());

            for (int i = 0; i < count2; i++) {
                start = System.currentTimeMillis();
                var targetList2 = StreamUtils.transformList2(sourceList2, MySourceRecord.class, MyTargetClass.class);
                end = System.currentTimeMillis();
                duration = end - start;

                if (verbose2) {
                    System.out.println(i + ": duration = " + duration);
                }

                times[i] = duration;

                if (verbose1) {
                    targetList2.forEach(System.out::println);
                }
            }

            System.out.println("Iteration " + j + ", Method 2: Max = " + Arrays.stream(times).max() + ", Min = " + Arrays.stream(times).min() + ", Avg = " + Arrays.stream(times).average());

            for (int i = 0; i < count2; i++) {
                start = System.currentTimeMillis();
                var targetList3 = Stream.generate(() -> new MyTargetClass()).limit(count1).toList();
                StreamUtils.transformList3(sourceList2, targetList3, MySourceRecord.class, MyTargetClass.class);
                end = System.currentTimeMillis();
                duration = end - start;

                if (verbose2) {
                    System.out.println(i + ": duration = " + duration);
                }

                times[i] = duration;

                if (verbose1) {
                    targetList3.forEach(System.out::println);
                }
            }
            System.out.println("Iteration " + j + ", Method 3: Max = " + Arrays.stream(times).max() + ", Min = " + Arrays.stream(times).min() + ", Avg = " + Arrays.stream(times).average());
        }
    }
}
