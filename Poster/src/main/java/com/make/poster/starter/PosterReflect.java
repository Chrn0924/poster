package com.make.poster.starter;

import com.guoxiaoxing.phoenix.core.listener.Processor;
import com.guoxiaoxing.phoenix.core.listener.Starter;
import com.make.poster.utils.MakePosterLog;

public class PosterReflect {


    public static final String posterix = "com.make.poster.starter.Posterix";

    public static Processor loadProcessor(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (Processor) clazz.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Posterix loadStarter(String className){
        MakePosterLog.e(className);
        try {
            MakePosterLog.e("!=null");
            Class<?> clazz = Class.forName(className);
            return (Posterix) clazz.newInstance();
        } catch (Throwable e) {
            MakePosterLog.e("null");
            e.printStackTrace();
            MakePosterLog.e(e.toString());
            return null;
        }
    }

}
