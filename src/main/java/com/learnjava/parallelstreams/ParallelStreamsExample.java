package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;


public class ParallelStreamsExample {

    public List<String> stringTransform(List<String> namesList){
        return namesList
                .stream()
                .map(this::transform)
                .parallel()
                .collect(Collectors.toList());
    }

    /*public List<String> stringTransform_upperCase(List<String> namesList){
        return namesList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }*/

    public List<String> stringTransform_1(List<String> namesList, boolean isParallel) {

        Stream<String> nameStream = namesList.stream();

        if(isParallel)
            nameStream.parallel();

        return nameStream
                .map(this::transform)
                .collect(Collectors.toList());
    }

    private String transform(String name) {
        delay(500);
        //log("Transforming : " + name);
        return name.length() + " - " + name;
    }

    public static void main(String[] args) {

        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        log("namesList : " + namesList);
        startTimer();
        ParallelStreamsExample parallelismExample = new ParallelStreamsExample();
        List<String> resultList =parallelismExample.stringTransform(namesList);
        timeTaken();
        log("resultList : " + resultList);
    }
}
