package org.pirkaengine.form.validator;

import org.pirkaengine.form.Messages;

/**
 * Validatorの基底クラス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 * @param <T>
 */
public abstract class ValidatorBase<T> implements Validator<T> {

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.Validator#validate(java.lang.String, java.lang.Object)
     */
    @Override
    public String validate(String name, T value) {
        if (!isValid(value)) {
            return Messages.getMessage(getMessageKey(), getMessageArgs(name, value));
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
    protected abstract String getMessageKey();

    /**
     * 値の妥当性をチェックする
     * @param value 値
     * @return 妥当な場合true
     */
    protected abstract boolean isValid(T value);

}
