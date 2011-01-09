package org.pirkaengine.wiki.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoldFormatter extends Formatter {

    static final Pattern PATTERN = Pattern.compile("\\*(.+?)\\*");

    @Override
    String format(String line) {
        Matcher m = PATTERN.matcher(line);
        if (!m.find()) return line;
        return format(getTagedText(line, m, "<strong>", "</strong>"));
    }

}
