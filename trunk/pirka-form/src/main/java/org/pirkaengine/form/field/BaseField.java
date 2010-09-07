package org.pirkaengine.form.field;

import java.util.ArrayList;
import java.util.List;

/**
 * フィールドの基底クラス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 * @param <T>
 */
public abstract class BaseField<T> {
    /** フォールド名 */
    public String name;
    /** ラベル */
    public String label;
    /** エラーメッセージ */
    public final List<String> errors = new ArrayList<String>();
    String rawText = "";
    T value;
    boolean required;

    /**
     * テキストをクリーニングし、型変換を行う。
     * <p>
     * 妥当性チェックも行う。
     * </p>
     * 
     * @return 型変換が成功した場合true
     */
    public boolean clean() {
        try {
            if (required && isEmptyValue()) {
                // TODO メッセージ
                errors.add(label + "を入力してください。");
                return false;
            }
            this.value = convert(rawText);
            return true;
        } catch (Exception e) {
            // TODO メッセージ
            errors.add(label + "を正しいフォーマットで入力してください。");
            return false;
        }
    }

    boolean isEmptyValue() {
        return this.rawText == null || this.rawText.isEmpty();
    }

    abstract protected T convert(String text);

    abstract protected String toString(T value);

    /**
     * フィールドがエラーを保持する場合にtrueを返す
     * 
     * @return エラーが存在する場合にtrue
     */
    public boolean hasError() {
        return !errors.isEmpty();
    }

    /**
     * rawTextを取得する.
     * 
     * @return rawText
     */
    public String getRawText() {
        return rawText;
    }

    /**
     * rawTextを指定して設定する.
     * 
     * @param rawText
     *            設定するrawText
     */
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    /**
     * valueを取得する.
     * 
     * @return value
     */
    public T getValue() {
        return value;
    }

    /**
     * valueを指定して設定する.
     * 
     * @param value
     *            設定するvalue
     */
    public void setValue(T value) {
        this.value = value;
        this.rawText = toString(value);
    }

    /**
     * 必須項目かどうかを設定する
     * 
     * @param required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 必須項目の場合trueを返す
     * 
     * @return 必須項目の場合true
     */
    public boolean isRequired() {
        return this.required;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.rawText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof BaseField<?>)) return false;
        BaseField<?> other = (BaseField<?>) obj;
        if (errors == null) {
            if (other.errors != null) return false;
        } else if (!errors.equals(other.errors)) return false;
        if (label == null) {
            if (other.label != null) return false;
        } else if (!label.equals(other.label)) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (rawText == null) {
            if (other.rawText != null) return false;
        } else if (!rawText.equals(other.rawText)) return false;
        if (required != other.required) return false;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rawText == null) ? 0 : rawText.hashCode());
        result = prime * result + (required ? 1231 : 1237);
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

}
