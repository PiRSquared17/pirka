package org.pirkaengine.wiki.impl;

import java.util.regex.Matcher;

abstract class Formatter {
    
    abstract String format(String line);

    static String getTagedText(String text, Matcher matcher, String tagStart, String tagEnd) {
        return text.substring(0, matcher.start()) + tagStart + matcher.group(1) + tagEnd
                + text.substring(matcher.end());
    }
}
