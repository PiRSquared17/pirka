package org.pirkaengine.mobile.charset;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.mobile.Emoji;

public class SoftbankSJISTest extends CharsetTestCase {

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        target = new SoftbankSJIS();
    }

    @Test
    @Override
    public void encode() throws Exception {
        super.encode();
    }


    @Test
    public void encode_emoji() throws Exception {
        assertThat(Emoji.BLACK_SUN_WITH_RAYS.toString().getBytes(target), is(toBytes(0xF98B)));
        assertThat(Emoji.CLOUD.toString().getBytes(target), is(toBytes(0xF98A)));
        assertThat(Emoji.UMBRELLA_WITH_RAIN_DROPS.toString().getBytes(target), is(toBytes(0xF98C)));
        assertThat(Emoji.SNOWMAN_WITHOUT_SNOW.toString().getBytes(target), is(toBytes(0xF989)));
        assertThat(Emoji.HIGH_VOLTAGE_SIGN.toString().getBytes(target), is(toBytes(0xF77D)));
        assertThat(Emoji.CYCLONE.toString().getBytes(target), is(toBytes(0xFB84)));
    }

    @Test
    @Override
    public void decode_ascii() throws Exception {
        super.decode_ascii();
    }

    @Test
    @Override
    public void decode_zenkana() throws Exception {
        super.decode_zenkana();
    }

    @Test
    @Override
    public void decode_hankana() throws Exception {
        super.decode_hankana();
    }

    @Test
    @Override
    public void decode_kanji() throws Exception {
        super.decode_kanji();
    }

    @Test
    @Override
    public void decode_kigou() throws Exception {
        super.decode_kigou();
    }
}
