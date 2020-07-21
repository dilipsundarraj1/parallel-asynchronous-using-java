package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;
import com.learnjava.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;

public class ForkJoinExampleUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinExampleUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }


    @Override
    /**
     * Recursively split the list and runs each half as a ForkJoinTask
     * Right way of using Fork/Join Task
     */
    protected List<String> compute() {

        if (this.inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(transform(name)));
            return resultList;
        }
        int midPoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinExampleUsingRecursion(inputList.subList(0, midPoint)).fork(); //left side of the list
        inputList = inputList.subList(midPoint, inputList.size()); //right side of the list
        List<String> rightResult = compute();
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }

    private String transform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinExampleUsingRecursion forkJoinExampleUsingRecursion = new ForkJoinExampleUsingRecursion(DataSet.namesList());
        stopWatch.start();


        List<String> resultList = forkJoinPool.invoke(forkJoinExampleUsingRecursion); // Start things running and get the result back, This is blocked until the results are calculated.

        LoggerUtil.log("resultList : " + resultList);

        stopWatch.stop();
        LoggerUtil.log("Total time taken : " + stopWatch.getTime());
    }

}
