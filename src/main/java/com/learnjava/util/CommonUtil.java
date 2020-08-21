package com.learnjava.util;

import org.apache.commons.lang3.time.StopWatch;

import static com.learnjava.util.LoggerUtil.log;
import static java.lang.Thread.sleep;

public class CommonUtil {

    public static StopWatch stopWatch = new StopWatch();

    public static void delay(long delayMilliSeconds)  {
        try{
            sleep(delayMilliSeconds);
        }catch (Exception e){
            LoggerUtil.log("Exception is :" + e.getMessage());
        }

    }

    public static String transForm(String s) {
        CommonUtil.delay(500);
        return s.toUpperCase();
    }

    public static void startTimer(){
        stopWatch.start();
    }

    public static void timeTaken(){
        stopWatch.stop();
        log("Total Time Taken : " +stopWatch.getTime());
    }

    public static void stopWatchReset(){
        stopWatch.reset();
    }

    public static  int noOfCores(){
        return Runtime.getRuntime().availableProcessors();
    }
}
