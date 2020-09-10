package com.learnjava.parallelstreams;

import java.util.List;

public class ReduceExample {

    public  int reduce_sum_ParallelStream(List<Integer> inputList){

        int sum= inputList
                .parallelStream()
                //.reduce(1, (x,y)->x+y);
                .reduce(0, (x,y)->x+y);
        return sum;
    }

    public  int reduce_multiply_parallelStream(List<Integer> inputList){
        int sum= inputList
                .parallelStream()
                .reduce(1, (x,y)->x*y);
        return sum;
    }

}