package org.pirkaengine.mobile.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Emoji {

    SortedMap<Integer, EmojiData> map = new TreeMap<Integer, EmojiData>();
    Map<String, String> fallbacks = new HashMap<String, String>();

    public Emoji() {
        scan();
        scanFallbackText();
        scanNames();
    }

    private void scan() {
        Scanner scanner = new Scanner(CodeMap.class.getResourceAsStream("/EmojiSrc.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.startsWith("#")) continue;
            // ; で終わる場合は最後の要素が含まれない
            String[] codes = line.split(";");
            EmojiData data = new EmojiData();
            if (codes[0].contains(" ")) continue;
            data.docomo = (codes[1].isEmpty()) ? 0 : Integer.parseInt(codes[1], 16);
            data.kddi = (codes.length <= 2 || codes[2].isEmpty()) ? 0 : Integer.parseInt(codes[2], 16);
            data.softbank = (codes.length <= 3 || codes[3].isEmpty()) ? 0 : Integer.parseInt(codes[3], 16);
            map.put(Integer.parseInt(codes[0], 16), data);
        }
        map.remove(0x26C5);
        scanner.close();
    }

    private void scanNames() {
        Scanner scanner = new Scanner(CodeMap.class.getResourceAsStream("/NamesList.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.matches("^(@|;|\\t).*")) continue;
            String[] names = line.split("\\t");
            assert names.length == 2;
            int codePoint = Integer.parseInt(names[0], 16);
            EmojiData data = map.get(codePoint);
            if (data == null) continue;
            data.name = names[1].replaceAll("-", "_").replaceAll(" ", "_").toUpperCase();
            String fallback = fallbacks.get(data.name);
            data.fallback = (fallback != null) ? fallback : "";
        }
        scanner.close();
    }

    private void scanFallbackText() {
        Scanner scanner = new Scanner(CodeMap.class.getResourceAsStream("/FallbackText.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] text = line.split("\\s");
            fallbacks.put(text[0], text[1]);
        }
        scanner.close();
    }

    void output() throws IOException {
        FileWriter writer = new FileWriter("target/emoji.txt");
        //        writer.write("static final int[] DB = new int[] {\n");
        for (Entry<Integer, EmojiData> entry : map.entrySet()) {
            EmojiData data = entry.getValue();
            if (data.name == null) throw new IllegalStateException("" + entry.getKey());
            if (data.needFallbackText()) {
                System.out.printf("%s(0x%X, 0x%X, 0x%X, 0x%X, \"[%s]\"),%n", data.name, entry.getKey(), data.docomo,
                        data.kddi, data.softbank, data.fallback);
            } else {
                System.out.printf("%s(0x%X, 0x%X, 0x%X, 0x%X),%n", data.name, entry.getKey(), data.docomo, data.kddi,
                        data.softbank);
            }
            //            writer.write(String.format("0x%X, // 0x%X%n", c, i));
        }
        //        writer.write("};");
        writer.close();
    }

    static class EmojiData {
        String name;
        String fallback;
        int docomo;
        int kddi;
        int softbank;

        boolean needFallbackText() {
            return docomo == 0 || kddi == 0 || softbank == 0;
        }
    }

    public static void main(String[] args) throws Exception {
        new Emoji().output();
    }
}
