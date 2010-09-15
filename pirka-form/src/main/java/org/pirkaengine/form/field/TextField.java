package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.Regex;
import org.pirkaengine.form.annotation.StartWith;
import org.pirkaengine.form.annotation.UriUsable;
import org.pirkaengine.form.validator.RegexValidator;
import org.pirkaengine.form.validator.StartWithValidator;
import org.pirkaengine.form.validator.UriUsableValidator;
import org.pirkaengine.form.validator.Validator;
import org.pirkaengine.form.validator.ValidatorBase;

/**
 * テキストフィールド.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class TextField extends BaseField<String> {

    /**
     * デフォルト値nullでフィールドを初期化する.
     */
    public TextField() {
        this(null);
    }

    /**
     * デフォルト値を指定してフィールドを初期化する
     * @param defaultValue デフォルト値
     */
    public TextField(String defaultValue) {
        setValue(defaultValue);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#getFieldType()
     */
    @Override
    public Class<String> getFieldType() {
        return String.class;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#getValidator(org.pirkaengine.form.FormMessage,
     *  java.lang.annotation.Annotation)
     */
    @Override
    protected Validator<String> getValidator(FormMessage message, Annotation anno) {
        Class<? extends Annotation> type = anno.annotationType();
        ValidatorBase<String> validator = null;
        if (type == StartWith.class) {
            return StartWithValidator.create(message, StartWith.class.cast(anno));
        } else if (type == Regex.class) {
            return RegexValidator.create(message, Regex.class.cast(anno));
        } else if (type == UriUsable.class) {
            return UriUsableValidator.create(message, UriUsable.class.cast(anno));
        }
        if (validator != null) {
            return validator;
        }
        return super.getValidator(message, anno);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#convert(java.lang.String)
     */
    @Override
    protected String convert(String text) {
        return text;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(String value) {
        if (value == null) return "";
        return value;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof TextField)) return false;
        return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
