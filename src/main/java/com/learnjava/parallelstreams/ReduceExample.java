package com.learnjava.parallelstreams;

import java.util.List;

public class ReduceExample {

    public  int reduce_sum_ParallelStream(){

        int sum= List.of(1,2,3,4,5,6,7, 8)
                .parallelStream()
                //.reduce(1, (x,y)->x+y);
                .reduce(0, (x,y)->x+y);
        return sum;
    }

    public  int reduce_multiply_parallelStream(){
        int sum= List.of(1,2,3,4)
                .parallelStream()
                .reduce(1, (x,y)->x*y);
        return sum;
    }

}
