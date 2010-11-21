package org.pirkaengine.core.function;


/**
 * 日付関連フォーマットクラス
 * @author shuji
 * @since 0.1
 */
public class Date {
    
    /**
     * yyyy/MM/ddのフォーマットで日付をフォーマットする.
     * @param date
     * @return yyyy/MM/dd
     */
    public static String format(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm").format(date);
    }
}
