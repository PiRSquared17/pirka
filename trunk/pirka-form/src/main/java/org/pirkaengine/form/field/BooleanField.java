package org.pirkaengine.form.field;


/**
 * Boolean型フィールド
 * @author shuji.w6e
 * @since 0.1.0
 */
public class BooleanField extends BaseField<Boolean> {

    /**
     * デフォルト値nullでフィールドを初期化する.
     */
    public BooleanField() {
        this(null);
    }

    /**
     * デフォルト値を指定してフィールドを初期化する
     * @param defaultValue デフォルト値
     */
    public BooleanField(Boolean defaultValue) {
        setValue(defaultValue);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#convert(java.lang.String)
     */
    @Override
    protected Boolean convert(String text) {
        return Boolean.valueOf(text);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#isEmptyValue()
     */
    @Override
    protected boolean isEmptyValue() {
        return !Boolean.valueOf(this.rawText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(Boolean value) {
        if (value == null) return "false";
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof BooleanField)) return false;
        return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.form.field.BaseField#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
