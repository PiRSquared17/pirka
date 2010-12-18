package org.pirkaengine.core.function;

public class Cond {

    public static String orBlank(boolean b, String text) {
        return or(b, text, "");
    }

    public static String or(boolean b, String textTrue, String textFalse) {
        return b ? textTrue : textFalse;
    }
}
