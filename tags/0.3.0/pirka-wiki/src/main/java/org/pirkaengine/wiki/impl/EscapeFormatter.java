package org.pirkaengine.wiki.impl;


class EscapeFormatter extends Formatter {

    @Override
    String format(String line) {
        if (line == null) throw new IllegalArgumentException("line == null");
        StringBuilder buf = new StringBuilder(line);
        for (int index = buf.length() - 1; 0 <= index; index--) {
            switch (buf.charAt(index)) {
            case '<':
                buf.replace(index, index + 1, "&lt;");
                break;
            case '>':
                buf.replace(index, index + 1, "&gt;");
                break;
            case '&':
                buf.replace(index, index + 1, "&amp;");
                break;
            case '"':
                buf.replace(index, index + 1, "&quot;");
                break;
            default:
                break;
            }
        }
        return buf.toString();
    }
}
