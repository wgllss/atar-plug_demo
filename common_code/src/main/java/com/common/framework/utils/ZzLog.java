package com.common.framework.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ZzLog {
    private static final boolean DEBUG = AppBuildConfig.isLog();
    private static final String TAG = "Zz-TAG";

    public static void v(String msg) {
        if (DEBUG)
            Log.v(TAG, getCaller() + msg);
    }

    public static void v(String msg, Throwable e) {
        if (DEBUG)
            Log.v(TAG, getCaller() + msg, e);
    }

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, getCaller() + msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, getCaller() + msg);
        }
    }

    public static void d(String msg, Throwable e) {
        if (DEBUG)
            Log.d(TAG, getCaller() + msg, e);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, getCaller() + msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, getCaller() + msg);
    }

    public static void i(String msg, Throwable e) {
        if (DEBUG)
            Log.i(TAG, getCaller() + msg, e);
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, getCaller() + msg);
    }

    public static void w(String msg, Throwable e) {
        if (DEBUG)
            Log.w(TAG, getCaller() + msg, e);
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, getCaller() + msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, getCaller() + msg);
    }

    public static void e(String msg, Throwable e) {
        if (DEBUG)
            Log.e(TAG, getCaller() + msg, e);
    }

    public static void e(Throwable e) {
        if (DEBUG)
            Log.e(TAG, getCaller() + crashToString(e));
    }

    private static String getCaller() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack.length < 5)
            return null;
        StackTraceElement caller = stack[4];
        String className = caller.getClassName();
        int shortIndex = className.lastIndexOf(".");
        if (shortIndex > 0)
            className = className.substring(shortIndex + 1, className.length());
        return "[" + className + " - " + caller.getLineNumber() + "] ";
    }

    private static String crashToString(Throwable ex) {
        StringBuffer buffer = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);
        return buffer.toString();
    }
}
