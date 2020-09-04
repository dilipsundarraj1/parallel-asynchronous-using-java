package com.learnjava.parallelstreams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReduceExampleTest {

    ReduceExample reduceExample = new ReduceExample();

    @Test
    void reduce_ParallelStream() {

        //given

        //when
        int result = reduceExample.reduce_sum_ParallelStream();

        //then
        assertEquals(36, result);
    }

    @Test
    void reduce() {

        //given

        //when
        int result = reduceExample.reduce_multiply_parallelStream();

        //then
        assertEquals(24, result);
    }
}