package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();


    @RepeatedTest(5)
    void multiplyEachValue() {

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        //then
        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    void multiplyEachValue_parallel() {

        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        //when
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        //then
        assertEquals(size, result.size());
    }

}