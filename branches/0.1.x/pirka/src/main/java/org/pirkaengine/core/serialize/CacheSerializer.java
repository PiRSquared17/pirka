package org.pirkaengine.core.serialize;

import java.io.IOException;

import org.pirkaengine.core.Template;


/**
 * Cache Serializer.
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface CacheSerializer {

    /**
     * テンプレートをキャッシュに出力する.
     * @param template テンプレート
     * @throws IOException 出力時のIO例外発生時
     */
    void serialize(Template template) throws IOException;
}