package org.pirkaengine.core.serialize;

import java.io.File;

import org.pirkaengine.core.PirkaContext;


/**
 * キャッシュの基底クラス.
 * <p>
 * コンテキストやテンプレートのファイル変換などを提供する.
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class CacheBase {

    PirkaContext context = PirkaContext.getInstance();

    /**
     * コンテキストの設定を行う
     * @param context
     */
    void setContext(PirkaContext context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        this.context = context;
    }
    
    File getTemplateFile(String templateName) {
        return new File(this.context.getCachePath() + templateName);
    }
}
