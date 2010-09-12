package org.pirkaengine.form.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pirkaengine.form.exception.ConvertException;


/**
 * 日付型フィールド.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class DateField extends BaseField<Date> {

    private final String dateFormat;

    /**
     * デフォルト値nullで「yyyy/MM/dd」のフォーマットで初期化する.
     */
    public DateField() {
        this((Date) null);
    }

    /**
     * デフォルト値を指定してフィールドを初期化する.
     * @param defaultValue デフォルト値
     */
    public DateField(Date defaultValue) {
        this(defaultValue, "yyyy/MM/dd");
    }

    /**
     * フォーマットを指定して初期化する.
     * @param dateFormat フォーマット
     */
    public DateField(String dateFormat) {
        this(null, dateFormat);
    }

    /**
     * フォーマットを指定して初期化する.
     * @param defaultValue 
     * @param dateFormat フォーマット
     */
    public DateField(Date defaultValue, String dateFormat) {
        if (dateFormat == null) throw new IllegalArgumentException("dateFormat == null");
        new SimpleDateFormat(dateFormat);
        this.dateFormat = dateFormat;
        setValue(defaultValue);
    }

    @Override
    protected Date convert(String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            return new SimpleDateFormat(dateFormat).parse(text);
        } catch (ParseException e) {
            throw new ConvertException(e, dateFormat);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(Date value) {
        if (value == null) return "";
        return new SimpleDateFormat(dateFormat).format(value);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DateField)) return false;
        if (!this.dateFormat.equals(((DateField) obj).dateFormat)) return false;
        return super.equals(obj);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#hashCode()
     */
    @Override
    public int hashCode() {
        return 17 * super.hashCode() + dateFormat.hashCode();
    }
}
