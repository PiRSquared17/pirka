package org.pirkaengine.form.validator;

import org.pirkaengine.form.FormMessage;

/**
 * Validatorの基底クラス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 * @param <T>
 */
public abstract class ValidatorBase<T> implements Validator<T> {
    
    /** FormMessage */
    protected FormMessage formMessage = FormMessage.NULL_MESSAGE;
    /** messageKey */
    protected final String messageKey;
    
    /**
     * Constructor.
     * @param messageKey メッセージキー
     */
    public ValidatorBase(String messageKey) {
        if (messageKey == null) throw new IllegalArgumentException("messageKey is null.");
        this.messageKey = messageKey;
    }
    
    /**
     * フォームメッセージを設定する
     * @param formMessage メッセージ
     */
    public void setFormMessage(FormMessage formMessage) {
        this.formMessage = formMessage;
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.Validator#validate(java.lang.String, java.lang.Object)
     */
    @Override
    public String validate(String name, T value) {
        if (!isValid(value)) {
            return formMessage.getFormMessage(getMessageKey(), getMessageArgs(name, value));
        }
        return null;
    }

    /**
     * メッセージパラメータを取得する。
     * <p>必要に応じてサブクラスでオーバーライドすること</p>
     * @param name 項目名
     * @param value 値
     * @return パラメータ
     */
    protected Object[] getMessageArgs(String name, T value) {
        return new Object[] { name };
    }

    /**
     * メッセージのキーを取得する.
     * @return メッセージキー
     */
    protected final String getMessageKey() {
        return messageKey;
    }

    /**
     * 値の妥当性をチェックする
     * @param value 値
     * @return 妥当な場合true
     */
    protected abstract boolean isValid(T value);

}
