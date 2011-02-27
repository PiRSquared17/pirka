package org.pirkaengine.wiki.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LinkFormatter extends Formatter {

    private static final Pattern LINKS_PATTERN = Pattern.compile("(http|https|ftp)://.+?( |$)|(\\[.+? .+?\\])",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern LINK_PATTERN = Pattern.compile("\\[(.+?) (.+?)\\]");
    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("^https?://.+\\.(png|jpg|jpeg|gif|bmp)$",
            Pattern.CASE_INSENSITIVE);

    @Override
    String format(String line) {
        List<String> links = new ArrayList<String>();
        Matcher link = LINKS_PATTERN.matcher(line);
        while (link.find()) {
            links.add(link.group().trim());
        }
        if (links.isEmpty()) return line;
        StringBuilder result = new StringBuilder(line);
        for (String l : links) {
            int idx = result.indexOf(l);
            result.replace(idx, idx + l.length(), toLink(l));
        }
        return result.toString();
    }

    String toLink(String link) {
        Matcher m = LINK_PATTERN.matcher(link);
        if (m.find()) {
            String url = m.group(1);
            String name = m.group(2);
            if (isImg(name)) {
                name = "<img src=\"" + name + "\" />";
            }
            return "<a href=\"" + url + "\">" + name + "</a>";
        } else if (isImg(link)) {
            return "<img src=\"" + link + "\" />";
        } else {
            return "<a href=\"" + link + "\">" + link + "</a>";
        }
    }

    private boolean isImg(String url) {
        return IMAGE_URL_PATTERN.matcher(url).matches();
    }

}
