package com.axistools.utils;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 *
 */
public class LogUtil {
    private static Logger log = Logger.getLogger(LogUtil.class);

    public static void debug(String msg) {
        log.debug(msg + getStackTrace());
    }

    public static void info(String msg) {
        log.info(msg + getStackTrace());
    }

    public static void info(String msg, Throwable t) {
        log.info(msg + getStackTrace(), t);
    }

    public static void info(String fmt, Object... args) {
        info(String.format(fmt, args));
    }

    public static void warn(String msg) {
        log.warn(msg + getStackTrace());
    }

    public static void warn(String msg, Throwable t) {
        log.warn(msg + getStackTrace(), t);
    }

    public static void warn(String fmt, Object... args) {
        warn(String.format(fmt, args));
    }

    public static void error(String msg) {
        log.error(msg + getStackTrace());
    }

    public static void error(String fmt, Object... args) {
        error(String.format(fmt, args));
    }

    public static void error(String msg, Throwable t) {
        log.error(msg + getStackTrace(), t);
    }

    public static void fatal(String msg) {
        log.fatal(msg + getStackTrace());
    }

    private static String getStackTrace() {
        int index = 2;
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StackTraceElement s = ste[index];
        String classname = s.getClassName();
        String method_name = s.getMethodName();
        String lineNum = String.valueOf(s.getLineNumber());
        return "  \t\t@[" + classname + "." + method_name + " => " + lineNum + "] ";
    }
}
