package org.pirkaengine.core.expression;


import org.junit.Assert;
import org.junit.Test;
import org.pirkaengine.core.expression.ExpressionUtil;

public class ExpressionUtilTest {

    @Test
    public void split_hoge() {
        Assert.assertArrayEquals(toArray("hoge"), ExpressionUtil.split("hoge"));
    }

    @Test
    public void split_hoge_huga() {
        Assert.assertArrayEquals(new String[] { "hoge", "huga" }, ExpressionUtil.split("hoge.huga"));
    }

    @Test
    public void split_foo_hoge() {
        Assert.assertArrayEquals(new String[] { "foo(hoge)" }, ExpressionUtil.split("foo(hoge)"));
    }

    @Test
    public void split_huga_foo_hoge() {
        Assert.assertArrayEquals(new String[] { "huga", "foo(hoge)" }, ExpressionUtil.split("huga.foo(hoge)"));
    }
    
    @Test
    public void split_huga_foo_hoge_hage() {
        Assert.assertArrayEquals(new String[] { "huga", "foo(hoge.hage)" }, ExpressionUtil.split("huga.foo(hoge.hage)"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_null() {
        ExpressionUtil.split(null);
    }
    
    private String[] toArray(String... str) {
        return str;
    }
}
