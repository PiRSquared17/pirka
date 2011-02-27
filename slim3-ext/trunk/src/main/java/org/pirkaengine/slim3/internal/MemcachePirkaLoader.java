package org.pirkaengine.slim3.internal;

import java.io.IOException;

import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.serialize.CacheDeserializer;
import org.pirkaengine.core.serialize.CacheManager;
import org.pirkaengine.core.serialize.CacheSerializer;
import org.slim3.memcache.Memcache;
import org.slim3.util.AppEngineUtil;

public class MemcachePirkaLoader extends PirkaLoader {

    private static final String PREFIX = "prk_tmpl_";

    /**
     * コンストラクタ.
     */
    public MemcachePirkaLoader() {
        super(new MemcacheCacheManager());
    }

    /**
     * Memcacheを使用するキャッシュマネージャ.
     */
    static class MemcacheCacheManager extends CacheManager {
        MemcacheCacheManager() {
            super(new MemcacheSerializer(), new MemcacheDeserializer());
        }
    }

    /**
     * Memcacheを使用するCacheSerializer.
     */
    static class MemcacheSerializer implements CacheSerializer {

        /*
         * (non-Javadoc)
         * @see org.pirkaengine.core.serialize.CacheSerializer#serialize(org.pirkaengine.core.Template)
         */
        @Override
        public void serialize(Template template) throws IOException {
            if (template == null) throw new IllegalArgumentException("template is null.");
            Memcache.put(PREFIX + template.getName(), template);
        }
    }

    /**
     * Memcacheを使用するCacheDeserializer.
     */
    static class MemcacheDeserializer implements CacheDeserializer {
        
        /*
         * (non-Javadoc)
         * @see org.pirkaengine.core.serialize.CacheDeserializer#deserialize(java.lang.String)
         */
        @Override
        public Template deserialize(String templateName) throws IOException, ClassNotFoundException {
            if (AppEngineUtil.isDevelopment()) return null;
            return Memcache.get(PREFIX + templateName);
        }

    }
}
