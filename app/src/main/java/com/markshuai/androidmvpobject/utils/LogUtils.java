package com.markshuai.androidmvpobject.utils;

import android.util.Log;

import com.markshuai.androidmvpobject.BuildConfig;

/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:15
 * 邮箱：mark_mingshuai@163.com
 * 意图：日志打印工具类
 */

public class LogUtils {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 获取当前类名
     *
     * @return
     */
    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }


    public static void w(String logString) {
        if (DEBUG) {
            Log.w(getClassName(), logString);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(getClassName(), msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(getClassName(), msg);
        }
    }

    public static void i(String tag, String logString) {
        if (DEBUG) {
            Log.i(tag, logString);
        }
    }


    public static void w(String tag, String logString) {
        if (DEBUG) {
            Log.w(tag, logString);
        }
    }


    public static void v(String tag, String logString) {
        if (DEBUG) {
            Log.v(tag, logString);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void v(String msg) {
        if (DEBUG) {
            Log.v(getClassName(), msg);
        }
    }
}
