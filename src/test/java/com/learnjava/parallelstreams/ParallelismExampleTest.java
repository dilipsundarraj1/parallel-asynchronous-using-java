package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelismExampleTest {

    ParallelismExample parallelismExample = new ParallelismExample();

    @Test
    void stringTransform() {
        List<String> stringList = parallelismExample.stringTransform(DataSet.namesList());
        assertEquals(4, stringList.size());
        stringList.forEach((name) -> {
            assertTrue(name.contains("-"));
        });
    }
}
