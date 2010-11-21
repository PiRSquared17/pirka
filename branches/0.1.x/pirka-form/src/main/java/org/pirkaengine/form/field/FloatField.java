package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.RangeFloat;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.validator.RangeFloatValidator;
import org.pirkaengine.form.validator.Validator;
import org.pirkaengine.form.validator.ValidatorBase;

/**
 * 小数型フィールド.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class FloatField extends BaseField<Float> {

    /**
     * デフォルト値nullでフィールドを初期化する.
     */
    public FloatField() {
        this(null);
    }

    /**
     * デフォルト値を指定してフィールドを初期化する.
     * @param defaultValue デフォルト値
     */
    public FloatField(Float defaultValue) {
        setValue(defaultValue);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#getFieldType()
     */
    @Override
    public Class<Float> getFieldType() {
        return Float.class;
    }

    @Override
    protected Float convert(String text) throws ConvertException {
        if (text == null || text.isEmpty()) return null;
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }
    
    @Override
    protected Validator<Float> getValidator(FormMessage message, Annotation anno) {
        Class<? extends Annotation> type = anno.annotationType();
        ValidatorBase<Float> validator = null;
        if (type == RangeFloat.class) {
            validator = RangeFloatValidator.create(message, RangeFloat.class.cast(anno));
        }
        if (validator != null) {
            return validator;
        }
        return super.getValidator(message, anno);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(Float value) {
        if (value == null) return "";
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof FloatField)) return false;
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
