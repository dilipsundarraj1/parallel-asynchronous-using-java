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

    public int sum_using_list(List<Integer> inputList, boolean isParallel){
        startTimer();
        Stream<Integer> inputStream = inputList.stream();

        if(isParallel)
            inputStream.parallel();

       int sum  = inputStream
                .mapToInt(Integer::intValue)
                .sum();
        timeTaken();
        return sum;
    }
}
