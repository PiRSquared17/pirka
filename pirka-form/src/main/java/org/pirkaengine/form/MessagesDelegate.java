package org.pirkaengine.form;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * メッセージ処理クラス。
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class MessagesDelegate {

    private final ThreadLocal<ResourceBundle> bundles = new ThreadLocal<ResourceBundle>();
    
    /**
     * リソースバンドルを指定して設定する
     * @param bundleName リソースバンドル名
     * @param locale ロケール
     */
    public void setBundle(String bundleName, Locale locale) {
        if (bundleName == null) throw new NullPointerException("The bundleName parameter is null.");
        if (locale == null) throw new NullPointerException("The locale parameter is null.");
        bundles.set(ResourceBundle.getBundle(bundleName, locale, Thread.currentThread().getContextClassLoader()));
    }
    
    /**
     * リソースバンドルをクリアする
     */
    public void clearBundle() {
        bundles.set(null);
    }
    
    /**
     * メッセージのキーとパラメータを指定してメッセージを構築する
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return 構築されたメッセージ
     */
    public String getMessage(String key, Object... args) {
        if (bundles.get() == null) return key;
        try {
            return MessageFormat.format(bundles.get().getString(key), args);
        } catch (MissingResourceException e) {
            return key;            
        }
    }
}
