package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;


public class ParallelismExample {

    public List<String> stringTransform(List<String> namesList){
        return namesList
                //.stream()
                .parallelStream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    /*public List<String> stringTransform_upperCase(List<String> namesList){
        return namesList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }*/



    private String transform(String name) {
        delay(500);
        //log("Transforming : " + name);
        return name.length() + " - " + name;
    }

    public static void main(String[] args) {

        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        startTimer();
        ParallelismExample parallelismExample = new ParallelismExample();
        List<String> resultList =parallelismExample.stringTransform(namesList);
        timeTaken();
        log("resultList : " + resultList);
    }
}
