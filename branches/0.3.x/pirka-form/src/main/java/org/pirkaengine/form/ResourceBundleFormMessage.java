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
public class ResourceBundleFormMessage implements FormMessage {
    private final ResourceBundle bundle;

    /**
     * デフォルトのリソースバンドルとデフォルトのロケールで初期化する。
     */
    public ResourceBundleFormMessage() {
        this("org.pirkaengine.form.messages");
    }

    /**
     * リソースバンドルを指定してデフォルトのロケールで初期化する。
     * @param bundleName リソースバンドル名
     */
    public ResourceBundleFormMessage(String bundleName) {
        this(bundleName, Locale.getDefault());
    }

    /**
     * リソースバンドルを指定して設定する
     * @param bundleName リソースバンドル名
     * @param locale ロケール
     */
    public ResourceBundleFormMessage(String bundleName, Locale locale) {
        if (bundleName == null) throw new NullPointerException("The bundleName parameter is null.");
        if (locale == null) throw new NullPointerException("The locale parameter is null.");
        bundle = ResourceBundle.getBundle(bundleName, locale, Thread.currentThread().getContextClassLoader());
    }

    /**
     * リソースバンドルを指定して設定する
     * @param bundle リソースバンドル
     */
    public ResourceBundleFormMessage(ResourceBundle bundle) {
        if (bundle == null) throw new NullPointerException("The bundle parameter is null.");
        this.bundle = bundle;
    }

    /**
     * メッセージのキーとパラメータを指定してメッセージを構築する
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return 構築されたメッセージ
     */
    public String getFormMessage(String key, Object... args) {
        try {
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException e) {
            return key;
        }
    }
}
