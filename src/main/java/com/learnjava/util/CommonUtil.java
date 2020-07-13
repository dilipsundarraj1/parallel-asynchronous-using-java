package com.learnjava.util;

import static java.lang.Thread.sleep;

public class CommonUtil {

    public static void delay(long delayMilliSeconds)  {
        try{
            sleep(delayMilliSeconds);
        }catch (Exception e){
            LoggerUtil.log("Exception is :" + e.getMessage());
        }

    }
}
