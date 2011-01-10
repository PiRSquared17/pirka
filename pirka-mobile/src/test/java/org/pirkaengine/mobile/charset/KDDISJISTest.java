package org.pirkaengine.mobile.charset;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.mobile.Emoji;

public class KDDISJISTest extends CharsetTestCase {

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        target = new KDDISJIS();
    }

    @Test
    @Override
    public void encode() throws Exception {
        super.encode();
    }

    @Test
    public void encode_emoji() throws Exception {
        assertThat(Emoji.BLACK_SUN_WITH_RAYS.toString().getBytes(target), is(toBytes(0xF660)));
        assertThat(Emoji.CLOUD.toString().getBytes(target), is(toBytes(0xF665)));
        assertThat(Emoji.UMBRELLA_WITH_RAIN_DROPS.toString().getBytes(target), is(toBytes(0xF664)));
        assertThat(Emoji.SNOWMAN_WITHOUT_SNOW.toString().getBytes(target), is(toBytes(0xF65D)));
        assertThat(Emoji.HIGH_VOLTAGE_SIGN.toString().getBytes(target), is(toBytes(0xF65F)));
        assertThat(Emoji.CYCLONE.toString().getBytes(target), is(toBytes(0xF641)));
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
