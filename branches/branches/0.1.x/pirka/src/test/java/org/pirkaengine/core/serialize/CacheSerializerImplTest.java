package org.pirkaengine.core.serialize;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.serialize.CacheSerializerImpl;

public class CacheSerializerImplTest {

    private CacheSerializerImpl target;

    @Before
    public void setup() {
        target = new CacheSerializerImpl();
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
        target.serialize(null);
    }


}
