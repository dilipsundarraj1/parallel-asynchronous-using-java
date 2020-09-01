package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {


    public static List<Integer> listOrder(List<Integer> inputList){
        return inputList.parallelStream()
                .map(i->i*2)
                .collect(Collectors.toList());

    }

    public static Set<Integer> listOrder_Set(List<Integer> inputList){
        return inputList.parallelStream()
                .map(i->i*2)
                .collect(Collectors.toSet());

    }

    public static Set<Integer> setOrder(Set<Integer> inputList){
        return inputList.parallelStream()
                .map(i->i*2)
                .collect(Collectors.toSet());
    }

    public static List<Integer> setOrder_List(Set<Integer> inputList){
        return inputList.parallelStream()
                .map(i->i*2)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
     /*   List<Integer> input = List.of(1,2,3,4,5,6,7,8,9);
        log("inputList : "+input);
        List<Integer> result = listOrder(input);
        log("result : "+result);*/



      /*  Set<Integer> input = Set.of(1,2,3,4,5,6,7,8,9);
        log("inputList : "+input);
        Set<Integer> result = setOrder(input);
        log("result : "+result);*/

          /* List<Integer> input = List.of(1,2,3,4,5,6,7,8,9);
        log("inputList : "+input);
        //List<Integer> result = listOrder_Set(input);
        log("result : "+listOrder_Set(input));*/

        Set<Integer> input = Set.of(1,2,3,4,5,6,7,8,9);
        log("inputList : "+input);
        log("result : "+setOrder_List(input));
    }
}
