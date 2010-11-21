package org.pirkaengine.core.serialize;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.serialize.CacheDeserializerImpl;
import org.pirkaengine.core.serialize.CacheManager;
import org.pirkaengine.core.serialize.CacheSerializerImpl;
import org.pirkaengine.core.serialize.SerializeException;
import org.pirkaengine.core.template.TextNode;
import org.pirkaengine.core.template.XhtmlTemplate;

public class CacheManagerTest {

    private CacheManager target;

    @Before
    public void setup() {
        PirkaContext context = new PirkaContext();
        context.setEnableCache(true);
        context.setCachePath("tmp/cache");
        CacheSerializerImpl serializer = new CacheSerializerImpl();
        serializer.setContext(context);
        CacheDeserializerImpl deserializer = new CacheDeserializerImpl();
        deserializer.setContext(context);
        target = new CacheManager(serializer, deserializer);
    }

    @Test
    public void serializeAndDeserialize_simple() throws SerializeException {
        String templateName = "simple";
        XhtmlTemplate template = new XhtmlTemplate(templateName);
        template.stack(new TextNode("<html><body></body></html>"));
        target.serialize(template);
        Template result = target.deserialize(template.getName());
        assertEquals(template, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void serialize_null() throws SerializeException {
        target.serialize(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deserialize_null() throws SerializeException {
        target.deserialize(null);
    }
}
