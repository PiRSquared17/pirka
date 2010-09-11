package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.pirkaengine.form.ConvertException;
import org.pirkaengine.form.annotation.Label;
import org.pirkaengine.form.annotation.Required;
import org.pirkaengine.form.exception.ValidatorFormatException;
import org.pirkaengine.form.validator.Validator;

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
    /** 値 */
    T value;
    /** 入力文字列 */
    String rawText = "";
    /** ラベル */
    public String label;
    /** エラーメッセージ */
    public final List<String> errors = new LinkedList<String>();
    /** 必須の場合true */
    boolean required;
    /** Validators */
    protected final List<Validator<T>> validators = new LinkedList<Validator<T>>();

    public void apply(String name, Annotation... annos) {
        this.name = name;
        this.label = name;
        for (Annotation anno : annos) {
            Class<?> type = anno.annotationType();
            if (type == Required.class) {
                this.setRequired(true);
            } else if (type == Label.class) {
                String anoLabel = ((Label) anno).value();
                if (anoLabel != null && !anoLabel.isEmpty()) this.label = anoLabel;
            } else {
                validators.add(getValidator(anno));
            }
        }
    }

    /**
     * アノテーションを指定してValidatorを取得する.
     * @param anno アノテーション
     * @return Validator
     * @throws ValidatorFormatException アノテーションの書式が不正な場合
     */
    protected Validator<T> getValidator(Annotation anno) throws ValidatorFormatException {
        throw new ValidatorFormatException("Undefined annotation: " + anno.annotationType().getName());
    }

    /**
     * テキストをクリーニングし、型変換を行う。
     * <p>
     * 必須チェック、型変換、妥当性チェックを行う。
     * </p>
     * @return 型変換が成功した場合true
     */
    public boolean clean() {
        if (isRequired() && isEmptyValue()) {
            // TODO メッセージ
            errors.add(label + "は必須項目です。");
            return !hasError();
        }
        try {
            this.value = convert(rawText);
        } catch (ConvertException e) {
            // TODO メッセージ
            errors.add(label + "を正しいフォーマットで入力してください。");
        }
        validate(this.value);
        return !hasError();
    }

    /**
     * 入力文字列が空文字の場合にtrueを返す
     * @return rawTextがnullまたは空文字列の場合にtrue
     */
    protected boolean isEmptyValue() {
        return this.rawText == null || this.rawText.isEmpty();
    }

    protected void validate(T value) {
        for (Validator<T> v : validators) {
            if (!v.isValid(this.value)) this.errors.add("");
        }
    }

    abstract protected T convert(String text) throws ConvertException;

    abstract protected String toString(T value);

    protected String createErrorMessage() {
        return null;
    }

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
     * @param required 必須項目の場合true
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 必須項目の場合trueを返す
     * @return 必須項目の場合true
     */
    public boolean isRequired() {
        return this.required;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.rawText;
    }

    /*
     * (non-Javadoc)
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
