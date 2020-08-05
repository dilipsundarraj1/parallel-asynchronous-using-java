package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;
import static java.lang.Thread.sleep;

public class StringTransformExample {

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        names.forEach((name)->{
            String newValue = addNameLengthTransform(name);
            resultList.add(newValue);
        });
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            log("Exception Occurred " + e);
        }
        return name.length()+" - "+name ;
    }
}
