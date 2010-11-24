package org.pirkaengine.core.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.pirkaengine.core.Template;


/**
 * Cache Deserializer.
 * <p>デフォルトの実装でファイルシステムを使ったデシリアライズを行う。</p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class CacheDeserializerImpl extends CacheBase implements CacheDeserializer {

    /**
     * コンストラクタ.
     */
    public CacheDeserializerImpl() {
    }

    /* (non-Javadoc)
     * @see org.pirkaengine.core.serialize.CacheDeserializer#deserialize(java.lang.String)
     */
    public Template deserialize(String templateName) throws IOException, ClassNotFoundException {
        if (templateName == null) throw new IllegalArgumentException("templateName == null");
        File file = this.getTemplateFile(templateName);
        if (!file.exists()) return null;
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            Object obj = input.readObject();
            return Template.class.cast(obj);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

}
