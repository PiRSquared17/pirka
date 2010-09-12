package org.pirkaengine.form.field;

import org.pirkaengine.form.exception.ConvertException;

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

    @Override
    protected Float convert(String text) throws ConvertException {
        if (text == null || text.isEmpty()) return null;
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
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
