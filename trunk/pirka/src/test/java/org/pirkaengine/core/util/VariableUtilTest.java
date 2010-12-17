package org.pirkaengine.core.util;

import junit.framework.Assert;

import org.junit.Test;
import org.pirkaengine.core.util.VariableUtil;

public class VariableUtilTest {

    @Test
    public void htmlEscape_変換無し() {
        String text = "こんにちは、世界。";
        Assert.assertEquals(text, VariableUtil.htmlEscape(text));
    }

    @Test(expected = IllegalArgumentException.class)
    public void htmlEscape_null() {
        VariableUtil.htmlEscape(null);
    }

    @Test
    public void htmlEscape_タグ() {
        String text = "こんにちは、<b>世界</b>。";
        Assert.assertEquals("こんにちは、&lt;b&gt;世界&lt;/b&gt;。", VariableUtil.htmlEscape(text));
    }

    @Test
    public void htmlEscape_ダブルクォート() {
        String text = "\"Hello\"";
        Assert.assertEquals("&quot;Hello&quot;", VariableUtil.htmlEscape(text));
    }

    @Test
    public void htmlEscape_バックスラッシュ() {
        String text = "\\1,000";
        Assert.assertEquals("&#39;1,000", VariableUtil.htmlEscape(text));
    }

    @Test
    public void htmlEscape_And() {
        String text = "D&D";
        Assert.assertEquals("D&amp;D", VariableUtil.htmlEscape(text));
    }

    @Test
    public void toNumber_int() {
        String value = "1";
        Assert.assertEquals(Integer.valueOf(1), VariableUtil.toNumber(value));
    }

    @Test
    public void toNumber_double() {
        String value = "2.8";
        Assert.assertEquals(Double.valueOf(2.8), VariableUtil.toNumber(value));
    }

    @Test
    public void toNumber_string() {
        String value = "string";
        Assert.assertNull(VariableUtil.toNumber(value));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toNumber_null() {
        VariableUtil.toNumber(null);
    }
}
