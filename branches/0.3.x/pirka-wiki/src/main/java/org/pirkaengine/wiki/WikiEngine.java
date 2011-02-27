package org.pirkaengine.wiki;

/**
 * WikiEngineインターフェイス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface WikiEngine {

    /**
     * ソースを指定し、wikiフォーマットを変換する
     * @param source source
     * @return formatted string 
     */
    String format(String source);
}
