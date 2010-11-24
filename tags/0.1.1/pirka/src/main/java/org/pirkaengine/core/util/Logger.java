package org.pirkaengine.core.util;

import java.util.logging.Level;

/**
 * Loggerユーティリティクラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public final class Logger {
    /** jdk standard logger */
    static final java.util.logging.Logger LOGGER;
    static {
        LOGGER = java.util.logging.Logger.getLogger("org.pirkaengine.core");
//        LOGGER.setLevel(Level.FINE);
    }

    /**
     * ログレベルを設定する.
     * @param newLevel
     */
    public static void setLevel(Level newLevel) {
        LOGGER.setLevel(newLevel);
    }

    /**
     * Traceログが有効な場合にtrueを返す.
     * @return Traceログが有効な場合にtrue
     */
    public static boolean isTraceEnabled() {
        return LOGGER.isLoggable(Level.FINER);
    }

    /**
     * Debugログが有効な場合にtrueを返す.
     * @return Debugログが有効な場合にtrue
     */
    public static boolean isDebugEnabled() {
        return LOGGER.isLoggable(Level.FINE);
    }

    /**
     * トレースログを出力する.
     * @param msg ログメッセージ
     */
    public static void trace(String msg) {
        log(Level.FINEST, msg, null);
    }

    /**
     * トレースログを出力する.
     * @param obj ログ対象オブジェクト
     */
    public static void trace(Object obj) {
        log(Level.FINEST, String.valueOf(obj), null);
    }

    /**
     * デバッグログを出力する.
     * @param msg ログメッセージ
     */
    public static void debug(String msg) {
        log(Level.FINE, msg, null);
    }

    /**
     * デバッグログを出力する.
     * @param obj ログ対象オブジェクト
     */
    public static void debug(Object obj) {
        log(Level.FINEST, String.valueOf(obj), null);
    }

    /**
     * INFOログを出力する.
     * @param msg ログメッセージ
     */
    public static void info(String msg) {
        log(Level.INFO, msg, null);
    }

    /**
     * エラーログを出力する.
     * @param msg ログメッセージ
     */
    public static void error(String msg) {
        log(Level.SEVERE, msg, null);
    }

    /**
     * エラーログを出力する.
     * @param msg ログメッセージ
     * @param t 原因となった例外
     */
    public static void error(String msg, Throwable t) {
        log(Level.SEVERE, msg, t);
    }

    // from commons-logging
    private static void log(Level level, String msg, Throwable ex) {
        if (!LOGGER.isLoggable(level)) return;
        // Hack (?) to get the stack trace.
        Throwable dummyException = new Throwable();
        StackTraceElement locations[] = dummyException.getStackTrace();
        // Caller will be the third element
        String cname = "unknown";
        String method = "unknown";
        if (locations != null && locations.length > 2) {
            StackTraceElement caller = locations[2];
            cname = caller.getClassName();
            method = caller.getMethodName();
        }
        if (ex == null) {
            LOGGER.logp(level, cname, method, msg);
        } else {
            LOGGER.logp(level, cname, method, msg, ex);
        }
    }

}
