package org.pirkaengine.form.field;

import org.pirkaengine.form.ConvertException;

/**
 * 整数型フィールド.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class IntegerField extends BaseField<Integer> {

    /**
     * デフォルト値nullでフィールドを初期化する.
     */
    public IntegerField() {
        this(null);
    }

    /**
     * デフォルト値を指定してフィールドを初期化する.
     * @param defaultValue デフォルト値
     */
    public IntegerField(Integer defaultValue) {
        setValue(defaultValue);
    }

    @Override
    protected Integer convert(String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(Integer value) {
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
        if (!(obj instanceof IntegerField)) return false;
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
