package com.example.hellobean.utils;

import android.util.Log;

/**
 * @author : wuzp
 * @date : 2020/5/19
 * @desc : 这个工具类是为了解决 跟踪方法调用链，追踪问题使用
 * 这个方法推荐在 debug 模式下使用
 */
public final class MethodInvokeUtil {

    public static final String sTag = "MethodInvokeUtil";

    //打印调用的栈信息
    public static void printInvokeStack() {
        StringBuilder tempBuilder = new StringBuilder();
        StackTraceElement[] currentThreadTrace = Thread.currentThread().getStackTrace();
        tempBuilder.append("\n ThreadName:").append(Thread.currentThread().getName()).append("\n");
        for (StackTraceElement element : currentThreadTrace) {
            tempBuilder.append("\t").append(element).append("\n");
        }
        Log.e(sTag, tempBuilder.toString());
    }

    //打印调用的栈信息
    public static void printInvokeStack(String tag) {
        StringBuilder tempBuilder = new StringBuilder();
        StackTraceElement[] currentThreadTrace = Thread.currentThread().getStackTrace();
        tempBuilder.append("\n ThreadName:").append(Thread.currentThread().getName()).append("\n");
        for (StackTraceElement element : currentThreadTrace) {
            tempBuilder.append("\t").append(element).append("\n");
        }
        Log.e(tag, tempBuilder.toString());
    }

    //打印调用的栈信息
    public static void printInvokeStack(String tag, String msg) {
        StackTraceElement[] currentThreadTrace = Thread.currentThread().getStackTrace();
        StringBuilder tempBuilder = new StringBuilder();
        tempBuilder.append("msg:")
                .append(msg)
                .append("\n");
        tempBuilder.append("\n ThreadName:").append(Thread.currentThread().getName()).append("\n");
        for (StackTraceElement element : currentThreadTrace) {
            tempBuilder.append("\t").append(element).append("\n");
        }
        Log.e(tag, tempBuilder.toString());
    }

    //打印调用的栈信息
    public static void printInvokeStackMsg(String msg) {
        StackTraceElement[] currentThreadTrace = Thread.currentThread().getStackTrace();
        StringBuilder tempBuilder = new StringBuilder();
        tempBuilder.append("msg:")
                .append(msg)
                .append("\n");
        tempBuilder.append("\n ThreadName:").append(Thread.currentThread().getName()).append("\n");
        for (StackTraceElement element : currentThreadTrace) {
            tempBuilder.append("\t").append(element).append("\n");
        }
        Log.e(sTag, tempBuilder.toString());
    }

}
