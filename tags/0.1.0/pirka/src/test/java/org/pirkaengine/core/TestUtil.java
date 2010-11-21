package org.pirkaengine.core;

import static org.pirkaengine.core.util.Logger.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pirkaengine.core.parser.LineBreak;


public class TestUtil {

    public static void assertRenderEquals(String templateName, StringBuffer actual) {
        assertRenderEquals(templateName, actual, "html");
    }

    public static void assertRenderEquals(String templateName, StringBuffer actual, String suffix) {
        assertRenderEquals(templateName, actual.toString(), suffix);
    }

    public static void assertRenderEquals(String templateName, String actual) {
        assertRenderEquals( templateName, actual, "html");
    }

    public static void assertRenderEquals(String templateName, String actual, String suffix) {
        debug(actual);
        String[] expectedLines = loadExpected(templateName, suffix);
        String[] actualLines = getLines(actual);
        StringBuffer errorMsg = new StringBuffer();
        if (expectedLines.length != actualLines.length) {
            errorMsg.append(String.format(
                "出力された行数が異なります。expected=%d, actual=%d\n",
                expectedLines.length,
                actualLines.length));
        }
        for (int i = 0; i < actualLines.length; i++) {
            if (expectedLines.length - 1 < i) break;
            if (!actualLines[i].equals(expectedLines[i])) {
                errorMsg.append(String.format(
                    "Line:%d expected/actual\n%s\n%s\n",
                    (i + 1),
                    expectedLines[i],
                    actualLines[i]));
            }
        }
        if (0 < errorMsg.length()) {
            errorMsg.setLength(errorMsg.length() - 1);
            throw new AssertionError(errorMsg.toString());
        }
    }

    private static String[] getLines(String text) {
        LineBreak lb = LineBreak.loolup(text);
        switch (lb) {
        case CRLF:
            return text.split("\\r\\n");
        case CR:
            return text.split("\\r");
        case LF:
            return text.split("\\n");
        default:
            return new String[] { text };
        }
    }

    public static String[] loadExpected(String templateName, String suffix) {
        LineNumberReader reader = null;
        try {
            File file = new File("testdata/" + getExpectedFileName(templateName, suffix));
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines.toArray(new String[lines.size()]);
        } catch (IOException e) {
            throw new AssertionError("ファイルのI/Oに失敗しました。:" + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                // do nothing
            }
        }

    }

    public static String getTemplateFileName(String templateName) {
        return getTemplateFileName(templateName, "html");
    }

    public static String getTemplateFileName(String templateName, String suffix) {
        return "Test." + templateName + ".template." + suffix;
    }

    public static String getExpectedFileName(String templateName, String suffix) {
        return "Test." + templateName + ".expected." + suffix;
    }

    public static String load(String templateName, String suffix) {
        InputStreamReader reader = null;
        try {
            String fileName = "testdata/" + getTemplateFileName(templateName, suffix);
            reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            StringBuilder str = new StringBuilder();
            char[] buf = new char[256 * 1024];
            int len = -1;
            while (0 < (len = reader.read(buf))) {
                str.append(buf, 0, len);
            }
            return str.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public static HashMap<String, Object> getModel(String key, Object value) {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put(key, value);
        return model;
    }
}
