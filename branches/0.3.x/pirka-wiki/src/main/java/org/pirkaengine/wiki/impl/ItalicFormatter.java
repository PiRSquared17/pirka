package org.pirkaengine.wiki.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ItalicFormatter extends Formatter {

    static final Pattern PATTERN = Pattern.compile("_(.+?)_");

    @Override
    String format(String line) {
        Matcher m = PATTERN.matcher(line);
        if (!m.find()) return line;
        return format(getTagedText(line, m, "<span style=\"font-style:italic;\">", "</span>"));
    }

}
