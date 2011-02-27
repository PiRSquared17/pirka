package org.pirkaengine.mobile.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CodeMap {

    // MS932 -> utf-8
    int[] decode = new int[0xFFFF];
    // utf-8 -> MS932
    int[] encode = new int[0xFFFF];

    public CodeMap() {
        scan();
    }

    private void scan() {
        Scanner scanner = new Scanner(CodeMap.class.getResourceAsStream("/bestfit932.txt"), "WINDOWS-31J");
        String line = scanner.nextLine();
        // decode / sjis -> unicode 
        while (!line.startsWith("MBTABLE")) {
            line = scanner.nextLine();
        }
        int mbcount = Integer.parseInt(line.split("\\s+")[1]); // 196
        line = scanner.nextLine();
        for (int i = 0; i < mbcount; i++) {
            // skip blank
            while (line.isEmpty()) {
                line = scanner.nextLine();
            }
            System.out.println(line);
            String[] codes = line.split("\\s+");
            int sjis = Integer.parseInt(codes[0].substring(2), 16);
            int utf8 = Integer.parseInt(codes[1].substring(2), 16);
            decode[sjis] = utf8;
            line = scanner.nextLine();
        }
        while (!line.startsWith("DBCSRANGE")) {
            line = scanner.nextLine();
        }
        int ranges = Integer.parseInt(line.split("\\s+")[1]); // 2
        line = scanner.nextLine(); // next
        for (int i = 0; i < ranges; i++) {
            // skip blank
            while (line.isEmpty()) {
                line = scanner.nextLine();
            }
            String[] range = line.split("\\s+");
            int from = Integer.parseInt(range[0].substring(2), 16);
            int to = Integer.parseInt(range[1].substring(2), 16);
            for (int j = from; j <= to; j++) {
                // skip to DBCSTABLE
                while (!line.startsWith("DBCSTABLE")) {
                    line = scanner.nextLine();
                }
                int count = Integer.parseInt(line.split("\\s+")[1]);
                line = scanner.nextLine();
                // skip blank
                while (line.isEmpty()) {
                    line = scanner.nextLine();
                }
                for (int k = 0; k < count; k++) {
                    String[] codes = line.split("\\s+");
                    int sjis = Integer.parseInt(Integer.toString(j, 16) + codes[0].substring(2), 16);
                    int utf8 = Integer.parseInt(codes[1].substring(2), 16);
                    decode[sjis] = utf8;
                    //                    System.out.printf("0x%X 0x%X%n", sjis, utf8);
                    line = scanner.nextLine();
                }
                //                System.out.println();
            }
        }
        // encode / unicode -> sjis
        while (!line.startsWith("WCTABLE")) {
            line = scanner.nextLine();
        }
        int count = Integer.parseInt(line.split("\\s+")[1]); // 9486
        line = scanner.nextLine();
        for (int i = 0; i < count; i++) {
            // skip blank
            while (line.isEmpty()) {
                line = scanner.nextLine();
            }
            String[] codes = line.split("\\s+");
            int utf8 = Integer.parseInt(codes[0].substring(2), 16);
            int sjis = Integer.parseInt(codes[1].substring(2), 16);
            encode[utf8] = sjis;
            //            System.out.printf("0x%x -> 0x%x%n", utf8, sjis);
            line = scanner.nextLine();
        }
        scanner.close();
    }

    void output() throws IOException {
        int from = -1;
        int pre = 0;
        int count = 0;
        for (int i = 0; i < encode.length; i++) {
            if (encode[i] != 0) count++;
            if (from == -1 && encode[i] != 0) {
                if (1000 < i - pre) {
                    count = 1;
                    System.out.printf("%n%d  0x%X-%n", (i - pre), i);
                }
                //                System.out.println((i - pre));
                //                System.out.printf("0x%X-", i);
                from = i;
            } else if (from != -1 && encode[i] == 0) {
                System.out.printf("  0x%X (%d/%d)", i - 1, i - from, count);
                from = -1;
                pre = i;
            }
        }
        output(decode, 0x00, 0xFF, 0xFFFD, "sjis2utf");
        output(decode, 0x8140, 0x9FFC, 0xFFFD, "sjis2utf");
        output(decode, 0xE040, 0xFC4B, 0xFFFD, "sjis2utf");

        output(encode, 0x0000, 0x0451, 0x3F, "utf2sjis");
        output(encode, 0x2010, 0x266F, 0x3F, "utf2sjis");
        output(encode, 0x3000, 0x33CD, 0x3F, "utf2sjis");

        output(encode, 0x4E00, 0x5FFF, 0x3F, "utf2sjis");
        output(encode, 0x6000, 0x7FFF, 0x3F, "utf2sjis");
        output(encode, 0x8000, 0x9FA0, 0x3F, "utf2sjis");

        output(encode, 0xE000, 0xE757, 0x3F, "utf2sjis");
        output(encode, 0xF8F0, 0xFA2D, 0x3F, "utf2sjis");
        output(encode, 0xFF01, 0xFFE5, 0x3F, "utf2sjis");
        FileWriter utf2sjisWriter = new FileWriter("utf2sjis.txt");
        utf2sjisWriter.write("int[] db = new int[] {");
        for (int i = 0; i < encode.length; i++) {
            // 0x3F
            if (encode[i] == 0) continue;
            utf2sjisWriter.write(String.format("0x%X, // 0x%X%n", encode[i], i));
            //            System.out.printf("0x%X, // 0x%X   0x%X # %c%n", b, i, b, (char) i);
        }
        utf2sjisWriter.write("};");
        utf2sjisWriter.close();
    }

    void output(int[] db, int from, int to, int defaultCode, String fileName) throws IOException {
        System.out.println(to - from + 1);
        FileWriter writer = new FileWriter(String.format("target/%s_0x%X_0x%X.txt", fileName, from, to));
        writer.write("static final int[] DB = new int[] {\n");
        for (int i = from; i <= to; i++) {
            int c = (db[i] == 0 && i != 0) ? defaultCode : db[i];
            writer.write(String.format("0x%X, // 0x%X%n", c, i));
            //            System.out.printf("0x%X, // 0x%X   0x%X # %c%n", (int) c, i, (int) c, c);
        }
        writer.write("};");
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        new CodeMap().output();
    }
}
