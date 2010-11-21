package org.pirkaengine.core.function;

import java.text.DecimalFormat;

public class Num {
    public static String format(String pattern, Object value) {
        return new DecimalFormat(pattern).format(value);
    }
    public static String paddingZero(int len, Number value) {
        StringBuilder format = new StringBuilder(len);
        for (int i = 0; i < len; i++) format.append('0');
        return format(format.toString(), value);
    }
}
