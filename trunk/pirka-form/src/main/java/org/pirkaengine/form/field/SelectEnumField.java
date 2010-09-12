package org.pirkaengine.form.field;

import java.util.Arrays;

import org.pirkaengine.form.ConvertException;


/**
 * Enum型セレクトフィールド.
 * @author shuji.w6e
 * @since 0.1.0
 * @param <T> 
 */
public class SelectEnumField<T extends Enum<T>> extends BaseField<T> {

    private final Class<T> enumClass;
    private final T[] options;

    /**
     * 型とオプションを指定して、フィールドを初期化する.
     * @param enumClass 列挙型
     * @param options オプション
     */
    public SelectEnumField(Class<T> enumClass, T[] options) {
        this(enumClass, options, null);
    }

    /**
     * 型とオプションを指定して、フィールドを初期化する。
     * @param enumClass 列挙型
     * @param options オプション
     * @param defaultValue デフォルト値
     */
    public SelectEnumField(Class<T> enumClass, T[] options, T defaultValue) {
        if (enumClass == null) throw new IllegalArgumentException("enumClass == null");
        if (options == null) throw new IllegalArgumentException("options == null");
        this.enumClass = enumClass;
        this.options = options;
        setValue(defaultValue);
    }

    /**
     * オプション項目を取得する.
     * @return オプション項目
     */
    public T[] options() {
        return options;
    }

    @Override
    protected T convert(String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            return Enum.valueOf(enumClass, text);
        } catch (IllegalArgumentException e) {
            throw new ConvertException(e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(T value) {
        if (value == null) return "";
        return value.name();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof SelectEnumField<?>)) return false;
        SelectEnumField<?> other = (SelectEnumField<?>) obj;
        if (this.enumClass != other.enumClass) return false;
        if (!Arrays.equals(this.options, other.options)) return false;
        return super.equals(obj);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 17 * result + this.enumClass.hashCode();
        result = 17 * result + Arrays.hashCode(options);
        return result;
    }
}
