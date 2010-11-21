package org.pirkaengine.core;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PirkaContext;

public class PirkaContextTest {

    private PirkaContext context;

    @Before
    public void setup() {
        context = new PirkaContext();
    }

    @Test
    public void setTemplatePath_スラッシュ有() {
        context.setTemplatePath("path/to/template/");
        Assert.assertEquals("path/to/template/", context.getTemplatePath());
    }

    @Test
    public void setTemplatePath_スラッシュ無() {
        context.setTemplatePath("path/to/template");
        Assert.assertEquals("path/to/template/", context.getTemplatePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTemplatePath_null() {
        context.setTemplatePath(null);
    }

}
