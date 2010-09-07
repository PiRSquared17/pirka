package org.pirkaengine.core.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.pirkaengine.core.Template;


/**
 * Cache Serializer.
 * <p>デフォルトの実装でファイルシステムを使ったシリアライズを行う。</p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class CacheSerializerImpl extends CacheBase implements CacheSerializer {

    /**
     * コンストラクタ.
     */
    public CacheSerializerImpl() {
    }

    /* (non-Javadoc)
     * @see org.pirkaengine.core.serialize.CacheSerializer#serialize(org.pirkaengine.core.Template)
     */
    public void serialize(Template template) throws IOException {
        if (template == null) throw new IllegalArgumentException("template == null");
        File file = this.getTemplateFile(template.getName());
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(template);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
}
