package com.gdc.gdcmvp.log;

import android.util.Log;

import com.gdc.gdcmvp.network.http.config.Constant;

/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.mvp.log
 * @file LogHelper
 * @brief 日志打印工具
 * @date 2017/8/11
 * @since appVer
 */
public final class LogHelper {
    /**
     * 是否打印日志
     */
    private static final boolean LOG = Constant.DEBUG_MODEL;

    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()  Line:%d";
    /**
     * 日志过滤器
     */
    private static final String LOG_TAG = "GDC";

    /**
     * 从异常堆栈中获取需要打印信息打印日志
     * @param objects
     */
    public static void trace(Object... objects) {
        if (LOG) {
            StackTraceElement traceElement = Thread.currentThread()
                    .getStackTrace()[3];// 从堆栈信息中获取当前被调用的方法信息
            String logText = String.format(CLASS_METHOD_LINE_FORMAT,
                    traceElement.getClassName(), traceElement.getMethodName(),
                    traceElement.getLineNumber());
            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    logText += "\n    Log:" + objects[i];
                }
            }
            Log.i(LOG_TAG, logText);// Log
        }
    }

    /**
     * 自定义tag日志打印输出 level Verbose
     * @param tag 自定义tag
     * @param msg 打印信息
     */
    public static void v(String tag, String msg) {
        if (LOG) {
            Log.v(tag, msg);
        }
    }

    /**
     * 自定义tag日志打印输出 level Verbose
     * @param tag 自定义tag
     * @param msg 打印信息
     * @param tr 异常类
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * 自定义tag日志打印输出 level Debug
     * @param tag 自定义tag
     * @param msg 打印信息
     */
    public static void d(String tag, String msg) {
        if (LOG) {
            Log.d(tag, msg);
        }
    }

    /**
     * 自定义tag日志打印输出 level Debug
     * @param tag 自定义tag
     * @param msg 打印信息
     * @param tr 异常类
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * 自定义tag日志打印输出 level Info
     * @param tag 自定义tag
     * @param msg 打印信息
     */
    public static void i(String tag, String msg) {
        if (LOG) {
            Log.i(tag, msg);
        }
    }

    /**
     * 自定义tag日志打印输出 level Debug
     * @param tag 自定义tag
     * @param msg 打印信息
     * @param tr 异常类
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * 自定义tag日志打印输出 level Warn
     * @param tag 自定义tag
     * @param msg 打印信息
     */
    public static void w(String tag, String msg) {
        if (LOG) {
            Log.w(tag, msg);
        }
    }

    /**
     * 自定义tag日志打印输出 level Warn
     * @param tag 自定义tag
     * @param msg 打印信息
     * @param tr 异常类
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * 自定义tag日志打印输出 level Error
     * @param tag 自定义tag
     * @param msg 打印信息
     */
    public static void e(String tag, String msg) {
        if (LOG) {
            Log.e(tag, msg);
        }
    }

    /**
     * 自定义tag日志打印输出 level Error
     * @param tag 自定义tag
     * @param msg 打印信息
     * @param tr 异常类
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * 使用默认tag打印日志
     * @param msg
     */
    public static void v(String msg) {
        LogHelper.v(LOG_TAG, msg);
    }

    public static void v(String msg, Throwable tr) {
        LogHelper.v(LOG_TAG, msg, tr);
    }

    public static void d(String msg) {
        LogHelper.d(LOG_TAG, msg);
    }

    public static void d(String msg, Throwable tr) {
        LogHelper.d(LOG_TAG, msg, tr);
    }

    public static void i(String msg) {
        LogHelper.i(LOG_TAG, msg);
    }

    public static void i(String msg, Throwable tr) {
        LogHelper.i(LOG_TAG, msg, tr);
    }

    public static void w(String msg) {
        LogHelper.w(LOG_TAG, msg);
    }

    public static void w(String msg, Throwable tr) {
        LogHelper.w(LOG_TAG, msg, tr);
    }

    public static void e(String msg) {
        LogHelper.i(LOG_TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        LogHelper.i(LOG_TAG, msg, tr);
    }
}
