package com.make.poster.utils;

import android.util.Log;

public class MakePosterLog {

    private static final String TAG = "MakePosterLog";

    private static boolean isPrintLog = true;

    public static void i(String msg) {
        if(isPrintLog){
            String logMessage = getLogMessage();
            Log.i(TAG, logMessage + " -> " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.i(tag, logMessage + " -> " + msg);
        }
    }

    public static void d(String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.d(TAG, logMessage + " -> " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.d(tag, logMessage + " -> " + msg);
        }
    }

    public static void v(String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.v(TAG, logMessage + " -> " + msg);
        }
    }

    public static void v(String tag, String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.v(tag, logMessage + " -> " + msg);
        }
    }

    public static void e(String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.e(TAG, logMessage + " -> " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.e(tag, logMessage + " -> " + msg);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if(isPrintLog) {
            String logMessage = getLogMessage();
            Log.e(tag, logMessage + " -> " + msg, throwable);
        }
    }

    public static String getLogMessage() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (null == stackTraceElements) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.isNativeMethod()) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(MakePosterLog.class.getName())) {
                continue;
            }
            return "[" + Thread.currentThread().getName() + ":("
                    + stackTraceElement.getFileName() + ":"
                    + stackTraceElement.getLineNumber() + "):"
                    + stackTraceElement.getMethodName() + "]";
        }
        return null;
    }

}
