package org.pirkaengine.mobile.charset;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.Before;

public class CharsetTestCase {

    Charset target;
    Charset ms932;

    @Before
    public void setUp() throws Exception {
        ms932 = Charset.forName("MS932");
    }

    String read(String resource) throws IOException {
        InputStream input = getClass().getResourceAsStream(resource);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, target));
            return reader.readLine();
        } finally {
            input.close();
        }
    }

    byte[] toBytes(int code) {
        byte b1 = (byte) (code >> 8 & 0xFF);
        byte b2 = (byte) (code & 0xFF);
        return new byte[] {
                b1, b2
        };
    }

    byte[] toBytes4(int code) {
        byte b1 = (byte) (code >> 24 & 0xFF);
        byte b2 = (byte) (code >> 16 & 0xFF);
        byte b3 = (byte) (code >> 8 & 0xFF);
        byte b4 = (byte) (code & 0xFF);
        return new byte[] {
                b1, b2, b3, b4
        };
    }
    
    public void encode() throws Exception {
        assertThat("hello".getBytes(target), is("hello".getBytes(ms932)));
        assertThat("こんにちは".getBytes(target), is("こんにちは".getBytes(ms932)));
        assertThat("コンニチハ".getBytes(target), is("コンニチハ".getBytes(ms932)));
        assertThat("ｺﾝﾆﾁﾊ".getBytes(target), is("ｺﾝﾆﾁﾊ".getBytes(ms932)));
        assertThat("珈琲休憩".getBytes(target), is("珈琲休憩".getBytes(ms932)));
        assertThat("①♪".getBytes(target), is("①♪".getBytes(ms932)));
        assertThat("〜".getBytes(target), is("〜".getBytes(ms932)));
    }

    public void decode_ascii() throws Exception {
        assertThat(read("ascii.txt"), is("hello"));
    }

    public void decode_zenkana() throws Exception {
        assertThat(read("zenkana.txt"), is("こんにちはせかい"));
    }

    public void decode_hankana() throws Exception {
        assertThat(read("hankana.txt"), is("ｱｲｳｴｵｶｷｸｹｺ"));
    }

    public void decode_kanji() throws Exception {
        assertThat(read("kanji.txt"), is("漢字試験"));
    }

    public void decode_kigou() throws Exception {
        assertThat(read("kigou.txt"), is("①↑〒☆"));
    }

}
