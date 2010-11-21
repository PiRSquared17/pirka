package org.pirkaengine.core.serialize;

import static org.junit.Assert.assertNull;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.serialize.CacheDeserializerImpl;

public class CacheDeserializerImplTest {

    private CacheDeserializerImpl target;

    @Before
    public void setup() {
        target = new CacheDeserializerImpl();
        PirkaContext ctx = new PirkaContext();
        ctx.setTemplatePath("tmp/test");
        target.setContext(ctx);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setContext_null() {
        target.setContext(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserialize_null() throws IOException, ClassNotFoundException {
        target.deserialize(null);
    }

    @Test
    public void deserialize_file_exist() throws IOException, ClassNotFoundException {
        assertNull(target.deserialize("test"));
    }

}
