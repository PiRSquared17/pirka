package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;

import org.pirkaengine.form.annotation.Regex;
import org.pirkaengine.form.annotation.StartWith;
import org.pirkaengine.form.annotation.UriUsable;
import org.pirkaengine.form.validator.RegexValidator;
import org.pirkaengine.form.validator.StartWithValidator;
import org.pirkaengine.form.validator.UriUsableValidator;
import org.pirkaengine.form.validator.Validator;

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
    
    @Override
    protected Validator<String> getValidator(Annotation anno) {
        Class<? extends Annotation> type = anno.annotationType();
        if (type == StartWith.class) {
            return new StartWithValidator(StartWith.class.cast(anno).value());
        } else if (type == Regex.class) {
            return new RegexValidator(Regex.class.cast(anno).value());
        } else if (type == UriUsable.class) {
            return new UriUsableValidator();
        }
        return super.getValidator(anno);
    }

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