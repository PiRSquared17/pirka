package org.pirkaengine.form.validator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.Regex;
import org.pirkaengine.form.annotation.Regex1;
import org.pirkaengine.form.annotation.Regex2;
import org.pirkaengine.form.annotation.Regex3;
import org.pirkaengine.form.annotation.Regex4;
import org.pirkaengine.form.annotation.Regex5;
import org.pirkaengine.form.annotation.Regex6;
import org.pirkaengine.form.annotation.Regex7;
import org.pirkaengine.form.annotation.Regex8;
import org.pirkaengine.form.annotation.Regex9;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 正規表現Validatorクラス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RegexValidator extends ValidatorBase<String> {

    private final Pattern pattern;

    /**
     * 正規表現パターンを指定してインスタンスを生成する.
     * @param regex 正規表現パターン
     * @throws ValidatorFormatException 正規表現パターンが不正な場合
     */
    public RegexValidator(String regex) throws ValidatorFormatException {
        this(regex, "validator.regex");
    }

    /**
     * 正規表現パターンを指定してインスタンスを生成する.
     * @param regex 正規表現パターン
     * @param messageKey メッセージキー
     * @throws ValidatorFormatException 正規表現パターンが不正な場合
     */
    public RegexValidator(String regex, String messageKey) throws ValidatorFormatException {
        super(messageKey);
        if (regex == null) throw new ValidatorFormatException("RegexValidator: " + regex);
        try {
            this.pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new ValidatorFormatException("RegexValidator: " + regex, e);
        }
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex1 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex2 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex3 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex4 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex5 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex6 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex7 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex8 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno Regexアノテーション
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, Regex9 anno) {
        return create(message, anno.value(), anno.messageKey());
    }

    /**
     * アノテーションを指定してRegexValidatorインスタンスを生成する
     * @param message FormMessage
     * @param value value
     * @param msgKey message key
     * @return Validator
     */
    public static RegexValidator create(FormMessage message, String value, String msgKey) {
        RegexValidator validator;
        if (msgKey == null || msgKey.isEmpty()) {
            validator = new RegexValidator(value);
        } else {
            validator = new RegexValidator(value, msgKey);
        }
        validator.setFormMessage(message);
        return validator;
    }

    @Override
    protected boolean isValid(String value) {
        return pattern.matcher(value).matches();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        assert messageKey != null;
        result = prime * result + messageKey.hashCode();
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
        RegexValidator other = (RegexValidator) obj;
        if (!pattern.pattern().equals(other.pattern.pattern())) return false;
        assert messageKey != null;
        if (!messageKey.equals(other.messageKey)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "RegexValidator[pattern=" + pattern + ", messageKey=" + messageKey + "]";
    }

}
