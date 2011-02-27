package org.pirkaengine.wiki.impl;

import java.util.regex.Pattern;

class BreakLineFormatter extends Formatter {

    static final Pattern PATTERN = Pattern.compile("\\[\\[BR\\]\\]", Pattern.CASE_INSENSITIVE);

    @Override
    String format(String line) {
        return PATTERN.matcher(line).replaceAll("<br/>");
    }

}
