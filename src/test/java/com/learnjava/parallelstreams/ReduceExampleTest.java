package com.learnjava.parallelstreams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReduceExampleTest {

    ReduceExample reduceExample = new ReduceExample();

    @Test
    void reduce_ParallelStream() {

        //given
        List<Integer> inputList = List.of(1,2,3,4,5,6,7, 8);

        //when
        int result = reduceExample.reduce_sum_ParallelStream(inputList);

        //then
        assertEquals(36, result);
    }

    @Test
    void reduce_ParallelStream_emptyList() {

        //given
        List<Integer> inputList = new ArrayList<>();

        //when
        int result = reduceExample.reduce_sum_ParallelStream(inputList);

        //then
        assertEquals(0, result);
    }

    @Test
    void reduce_multiply() {

        //given
        List<Integer> inputList = List.of(1,2,3,4);

        //when
        int result = reduceExample.reduce_multiply_parallelStream(inputList);

        //then
        assertEquals(24, result);
    }

    @Test
    void reduce_multiply_emptyList() {

        //given
        List<Integer> inputList = new ArrayList<>();

        //when
        int result = reduceExample.reduce_multiply_parallelStream(inputList);

        //then
        assertEquals(1, result);
    }
}