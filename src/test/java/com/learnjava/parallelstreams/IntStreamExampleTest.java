package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IntStreamExampleTest {

    IntStreamExample intStreamExample = new IntStreamExample();

    @Test
    void sum() {
        //given

        //when
        int sum = intStreamExample.sum(1000000, false);
        System.out.println("sum : "+ sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sum_parallel() {
        //given

        //when
        int sum = intStreamExample.sum(1000000, true);
        System.out.println("sum : "+ sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sum_iterate() {
        //given

        //when
        int sum = intStreamExample.sum_iterate(1000000, false);
        System.out.println("sum : "+ sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sum_iterate_parallel() {
        //given

        //when
        int sum = intStreamExample.sum_iterate(1000000, true);
        System.out.println("sum : "+ sum);

        //then
        assertEquals(1784293664, sum);
    }

}