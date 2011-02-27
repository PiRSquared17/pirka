package org.pirkaengine.mobile.charset;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.mobile.Emoji;

/**
 * Test for {@link DocomoSJIS}
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class DocomoSJISTest extends CharsetTestCase {

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        target = new DocomoSJIS();
    }

    @Test
    @Override
    public void encode() throws Exception {
        super.encode();
    }

    @Test
    public void encode_emoji() throws Exception {
        assertThat(Emoji.BLACK_SUN_WITH_RAYS.toString().getBytes(target), is(toBytes(0xF89F)));
        assertThat(Emoji.CLOUD.toString().getBytes(target), is(toBytes(0xF8A0)));
        assertThat(Emoji.UMBRELLA_WITH_RAIN_DROPS.toString().getBytes(target), is(toBytes(0xF8A1)));
        assertThat(Emoji.SNOWMAN_WITHOUT_SNOW.toString().getBytes(target), is(toBytes(0xF8A2)));
        assertThat(Emoji.HIGH_VOLTAGE_SIGN.toString().getBytes(target), is(toBytes(0xF8A3)));
        assertThat(Emoji.CYCLONE.toString().getBytes(target), is(toBytes(0xF8A4)));
        assertThat(Emoji.SUN_BEHIND_CLOUD.toString().getBytes(target), is(toBytes4(0xF89FF8A0)));
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
