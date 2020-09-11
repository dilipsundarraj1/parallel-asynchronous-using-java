package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class IntStreamExample {

    public int sum(int count, boolean isParallel){
        startTimer();
        IntStream intStream = IntStream.rangeClosed(0,count);

        if(isParallel)
            intStream.parallel();

        int sum = intStream
                .sum();
        timeTaken();
        return sum;
    }

    public int sum_iterate(int count, boolean isParallel){
        startTimer();
         Stream<Integer> integerStream = Stream.iterate(0, n ->n+1 );


        if(isParallel)
            integerStream.parallel();

        int sum = integerStream
                .limit(count+1)
                .reduce(0, Integer::sum);

        timeTaken();
        return sum;
    }

}
