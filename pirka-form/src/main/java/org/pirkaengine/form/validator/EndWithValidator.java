package org.pirkaengine.form.validator;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.EndWith;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 特定の文字列で終了されているかを判定するValidator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EndWithValidator extends ValidatorBase<String> {

    private final String suffix;

    /**
     * サフィックスを指定してインスタンスを生成する.
     * @param suffix サフィックス
     */
    public EndWithValidator(String suffix) {
        this(suffix, "validator.endWith");
    }

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param suffix サフィックス
     * @param messageKey メッセージキー
     */
    public EndWithValidator(String suffix, String messageKey) {
        super(messageKey);
        if (suffix == null || suffix.isEmpty()) throw new ValidatorFormatException("Illegal suffix: " + suffix);
        this.suffix = suffix;
    }

    /**
     * アノテーションを指定してStartWithValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno EndWithアノテーション
     * @return Validator
     */
    public static EndWithValidator create(FormMessage message, EndWith anno) {
        String value = anno.value();
        String msgKey = anno.messageKey();
        EndWithValidator validator;
        if (msgKey == null || msgKey.isEmpty()) {
            validator = new EndWithValidator(value);
        } else {
            validator = new EndWithValidator(value, msgKey);
        }
        validator.setFormMessage(message);
        return validator;
    }

    @Override
    protected boolean isValid(String value) {
        if (value == null) throw new IllegalArgumentException("value is null.");
        return value.endsWith(suffix);
    }

    @Override
    protected Object[] getMessageArgs(String name, String value) {
        return new Object[] { name, suffix };
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((suffix == null) ? 0 : suffix.hashCode());
        assert messageKey != null;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EndWithValidator other = (EndWithValidator) obj;
        if (suffix == null) {
            if (other.suffix != null) return false;
        } else if (!suffix.equals(other.suffix)) return false;
        assert messageKey != null;
        if (!messageKey.equals(other.messageKey)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EndWithValidator[prefix=" + suffix + ", messageKey=" + messageKey + "]";
    }

}
