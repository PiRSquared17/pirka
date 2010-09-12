package org.pirkaengine.form;

import java.util.Locale;

/**
 * メッセージを管理する.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public final class Messages {

    private final static MessagesDelegate delegate = new MessagesDelegate();

    private Messages() {
    }

    /**
     * リソースバンドルを指定して設定する
     * @param bundleName リソースバンドル名
     * @param locale ロケール
     */
    public static void setBundle(String bundleName, Locale locale) {
        delegate.setBundle(bundleName, locale);
    }

    /**
     * リソースバンドルをクリアする
     */
    public static void clearBundle() {
        delegate.clearBundle();
    }

    /**
     * メッセージのキーとパラメータを指定してメッセージを構築する
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return 構築されたメッセージ
     */
    public static String getMessage(String key, Object... args) {
        return delegate.getMessage(key, args);
    }
}
