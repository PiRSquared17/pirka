package org.pirkaengine.slim3.internal;

import java.io.IOException;

import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.serialize.CacheDeserializer;
import org.pirkaengine.core.serialize.CacheManager;
import org.pirkaengine.core.serialize.CacheSerializer;
import org.slim3.datastore.Datastore;
import org.slim3.util.AppEngineUtil;
import org.slim3.util.ByteUtil;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class DatastorePirkaLoader extends PirkaLoader {

    private static final String KIND = "prk_tmpl";
    private static final String PROP_META = "m";

    /**
     * コンストラクタ.
     */
    public DatastorePirkaLoader() {
        super(new DatastoreCacheManager());
    }

    /**
     * Datastoreを使用するキャッシュマネージャ.
     */
    static class DatastoreCacheManager extends CacheManager {
        DatastoreCacheManager() {
            super(new DatastoreSerializer(), new DatastoreDeserializer());
        }
    }

    /**
     * Datastoreを使用するCacheSerializer.
     */
    static class DatastoreSerializer implements CacheSerializer {
        
        /*
         * (non-Javadoc)
         * @see org.pirkaengine.core.serialize.CacheSerializer#serialize(org.pirkaengine.core.Template)
         */
        @Override
        public void serialize(Template template) throws IOException {
            if (template == null) throw new IllegalArgumentException("template is null.");
            Entity entity = new Entity(KIND, template.getName());
            entity.setUnindexedProperty(PROP_META, new Blob(ByteUtil.toByteArray(template)));
            Datastore.put(entity);
        }
    }

    /**
     * Datastoreを使用するCacheDeserializer.
     */
    static class DatastoreDeserializer implements CacheDeserializer {

        /*
         * (non-Javadoc)
         * @see org.pirkaengine.core.serialize.CacheDeserializer#deserialize(java.lang.String)
         */
        @Override
        public Template deserialize(String templateName) throws IOException, ClassNotFoundException {
            if (AppEngineUtil.isDevelopment()) return null;
            Key key = Datastore.createKey(KIND, templateName);
            Entity entity = Datastore.getOrNullWithoutTx(key);
            if (entity == null) return null;
            Blob b = (Blob) entity.getProperty(PROP_META);
            return ByteUtil.toObject(b.getBytes());
        }

    }

}
