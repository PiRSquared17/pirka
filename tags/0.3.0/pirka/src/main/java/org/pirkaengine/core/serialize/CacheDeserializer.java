package org.pirkaengine.core.serialize;

import java.io.IOException;

import org.pirkaengine.core.Template;


/**
 * Cache Deserializer.
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface CacheDeserializer {

    /**
     * テンプレート名を指定して、キャッシュからデシリアライズを行う。
     * <p>
     * キャッシュが存在しない場合はnullを返す
     * </p>
     * @param templateName テンプレート名
     * @return デシリアライズされたテンプレート
     * @throws IOException
     * @throws ClassNotFoundException
     */
    Template deserialize(String templateName) throws IOException, ClassNotFoundException;

}