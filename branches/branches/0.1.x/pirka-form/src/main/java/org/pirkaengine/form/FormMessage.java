package org.pirkaengine.form;

/**
 * フォームメッセージ・インターフェイス。
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface FormMessage {

    /** NULL_MESSAGE. */
    FormMessage NULL_MESSAGE = new FormMessage() {

        /*
         * (non-Javadoc)
         * @see org.pirkaengine.form.FormMessage#getFormMessage(java.lang.String, java.lang.Object[])
         */
        @Override
        public String getFormMessage(String key, Object... args) {
            return "";
        }
    };

    /**
     * メッセージのキーとパラメータを指定してメッセージを構築する
     * @param key メッセージキー
     * @param args メッセージパラメータ
     * @return 構築されたメッセージ
     */
    String getFormMessage(String key, Object... args);
}
