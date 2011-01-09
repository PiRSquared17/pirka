package org.pirkaengine.wiki.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
class Convertor {

    /** line separator */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    static final Pattern[] HEADER_PATTEN = new Pattern[] {
            Pattern.compile("^= (.+) =$"),
            Pattern.compile("^== (.+) ==$"),
            Pattern.compile("^=== (.+) ===$"),
            Pattern.compile("^==== (.+) ====$"),
            Pattern.compile("^===== (.+) =====$"),
            Pattern.compile("^====== (.+) ======$"),
    };
    static final Pattern DIVIDER_PATTEN = Pattern.compile("^-{4,}$");

    final StringBuilder buf = new StringBuilder();
    final Scanner scanner;
    final Formatter[] formatters = new Formatter[] {
            new EscapeFormatter(),
            new BreakLineFormatter(),
            new ItalicFormatter(),
            new BoldFormatter(),
            new StrikeFormatter(),
            new LinkFormatter(),
    };
    Mode mode = null;

    enum Mode {
        PARAGRAPH, LISTS, OL
    }

    Convertor(String source) {
        scanner = new Scanner(source);
    }

    String convert() {
        LinkedList<String> linesBuf = new LinkedList<String>();
        outer: while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                createBlock(linesBuf);
                continue;
            }
            // Divider
            Matcher divider = DIVIDER_PATTEN.matcher(line);
            if (divider.matches()) {
                createBlock(linesBuf);
                buf.append("<hr/>").append(LINE_SEPARATOR);
                continue;
            }
            // Header
            for (int i = 0; i < HEADER_PATTEN.length; i++) {
                int lv = i + 1;
                Matcher m = HEADER_PATTEN[i].matcher(line);
                if (m.find()) {
                    createBlock(linesBuf);
                    buf.append(LinkFormatter.getTagedText(line, m, "<h" + lv + ">", "</h" + lv + ">")).append(
                            LINE_SEPARATOR);
                    continue outer;
                }
            }
            if (line.matches("^ +(\\*|#) .*")) { // Lists
                if (mode != Mode.LISTS) createBlock(linesBuf);
                mode = Mode.LISTS;
            } else { // P
                if (mode != Mode.PARAGRAPH) createBlock(linesBuf);
                mode = Mode.PARAGRAPH;
            }
            linesBuf.add(line);
        }
        createBlock(linesBuf);
        return buf.toString();
    }

    private void createBlock(Queue<String> lines) {
        if (lines.isEmpty()) return;
        switch (mode) {
        case LISTS:
            createLists(lines, 1);
            break;
        default:
            createParagraph(lines);
            break;
        }
    }

    private void createLists(Queue<String> lines, int level) {
        assert !lines.isEmpty();
        boolean firstItem = true;
        boolean ordered = lines.peek().charAt(level) == '#';
        String startTag = ordered ? "<ol>" : "<ul>";
        String endTag = ordered ? "</ol>" : "</ul>";
        buf.append(startTag).append(LINE_SEPARATOR);
        while (!lines.isEmpty()) {
            String line = lines.peek();
            int lv = countHeadWhiteSpace(line);
            if (lv < level) {
                buf.append("</li>").append(LINE_SEPARATOR);
                buf.append(endTag).append(LINE_SEPARATOR);
                return;
            } else if (lv == level) {
                if (firstItem) {
                    firstItem = false;
                } else {
                    buf.append("</li>").append(LINE_SEPARATOR);
                }
                buf.append("<li>").append(format(line.substring(lv + 2)));
                lines.poll();
            } else {
                // nested lists
                buf.append(LINE_SEPARATOR);
                createLists(lines, lv);
            }
        }
        buf.append("</li>").append(LINE_SEPARATOR);
        buf.append(endTag).append(LINE_SEPARATOR);
    }

    private int countHeadWhiteSpace(String line) {
        int count = 0;
        while (line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    private void createParagraph(Queue<String> lines) {
        assert !lines.isEmpty();
        buf.append("<p>");
        for (Iterator<String> iter = lines.iterator(); iter.hasNext();) {
            String line = iter.next();
            buf.append(format(line));
            if (iter.hasNext()) buf.append(LINE_SEPARATOR);
        }
        buf.append("</p>").append(LINE_SEPARATOR);
        lines.clear();
    }

    private String format(String line) {
        String result = line;
        for (Formatter f : formatters) {
            result = f.format(result);
        }
        return result;
    }

}
