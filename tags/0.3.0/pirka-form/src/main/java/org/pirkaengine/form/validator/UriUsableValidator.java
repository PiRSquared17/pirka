package org.pirkaengine.form.validator;

import java.util.regex.Pattern;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.UriUsable;

/**
 * 特定の文字列で開始されているかを判定するValidator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class UriUsableValidator extends ValidatorBase<String> {

    private static final Pattern PATTERN = Pattern.compile("[\\w-\\./~,$!\\*';:@=&+]*");

    /**
     * Constructor.
     */
    public UriUsableValidator() {
        this("validator.uriUsable");
    }

    /**
     * Constructor.
     * @param messageKey メッセージキー
     */
    public UriUsableValidator(String messageKey) {
        super(messageKey);
    }

    /**
     * アノテーションを指定してUriUsableValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno UriUsableアノテーション
     * @return UriUsableValidator
     */
    public static UriUsableValidator create(FormMessage message, UriUsable anno) {
        String msgKey = anno.messageKey();
        UriUsableValidator validator;
        if (msgKey == null || msgKey.isEmpty()) {
            validator = new UriUsableValidator();
        } else {
            validator = new UriUsableValidator(msgKey);
        }
        validator.setFormMessage(message);
        return validator;
    }

    @Override
    protected boolean isValid(String value) {
        return PATTERN.matcher(value).matches();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        assert messageKey != null;
        result = prime * result + messageKey.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (getClass() != obj.getClass()) return false;
        UriUsableValidator other = (UriUsableValidator) obj;
        assert messageKey != null;
        if (!messageKey.equals(other.messageKey)) return false;
        return true;
    }
}
