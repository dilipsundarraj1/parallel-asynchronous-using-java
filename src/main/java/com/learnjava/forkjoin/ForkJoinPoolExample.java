package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;
import com.learnjava.util.LoggerUtil;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;

public class ForkJoinPoolExample extends RecursiveTask<String> {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    private List<String> inputList;

    public ForkJoinPoolExample(List<String> inputList) {
        this.inputList = inputList;
    }


    @Override
    protected String compute() {
        if(inputList.size() == 1) {
            return transform(inputList.get(0));
        }

        List<String> oneStores = inputList.subList(0, inputList.size()/2);
        List<String> twoStores = inputList.subList(inputList.size()/2,
                inputList.size());

        ForkJoinPoolExample cTaskOne = new ForkJoinPoolExample(oneStores);
        cTaskOne.fork();

        ForkJoinPoolExample cTaskTwo = new ForkJoinPoolExample(twoStores);

        return cTaskTwo.compute()+"," + cTaskOne.join();

        // Split the list by half recursively until there is a single element

    }

    private String transform(String s) {
        delay(500);
        return s.length()+" - " + s;
    }

    public static void main(String[] args) {

        ForkJoinPoolExample forkJoinPoolExample = new ForkJoinPoolExample(DataSet.namesList());
        StopWatch stopWatch= new StopWatch();
        stopWatch.start();
        String output = FORK_JOIN_POOL.invoke(forkJoinPoolExample);
        stopWatch.stop();
        LoggerUtil.log("Total time taken : "+ stopWatch.getTime());
        LoggerUtil.log(output);
    }
}
