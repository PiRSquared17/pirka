package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.Label;
import org.pirkaengine.form.annotation.Required;
import org.pirkaengine.form.exception.ConvertException;
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
    protected String name;
    /** 値 */
    protected T value;
    /** 入力文字列 */
    protected String rawText = "";
    /** ラベル */
    protected String label;
    /** 必須の場合true */
    protected boolean required;
    /** Validators */
    protected final List<Validator<T>> validators = new LinkedList<Validator<T>>();
    /** エラーメッセージ */
    protected final List<String> errors = new LinkedList<String>();
    /** FormMessage */
    protected FormMessage message = FormMessage.NULL_MESSAGE;
    String requiredMessageKey = "validator.required";

    /**
     * フィールドの型を返す.
     * @return フィールドの型クラス
     */
    public abstract Class<T> getFieldType();

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * フィールドの各種パラメータを適用する.
     * @param aName フィールド名
     * @param aMessage フォームメッセージ
     * @param annos アノテーション
     */
    public void apply(String aName, FormMessage aMessage, Annotation... annos) {
        this.name = aName;
        this.label = aName;
        this.message = aMessage;
        for (Annotation anno : annos) {
            Class<?> type = anno.annotationType();
            if (type == Required.class) {
                this.setRequired(true);
                String messageKey = Required.class.cast(anno).messageKey();
                if (messageKey != null && !messageKey.isEmpty()) {
                    this.requiredMessageKey = messageKey;
                }
            } else if (type == Label.class) {
                String anoLabel = ((Label) anno).value();
                this.label = aMessage.getFormMessage(anoLabel);
            } else {
                this.validators.add(getValidator(aMessage, anno));
            }
        }
    }

    /**
     * アノテーションを指定してValidatorを取得する.
     * @param formMessage フォームメッセージ
     * @param anno アノテーション
     * @return Validator
     * @throws ValidatorFormatException アノテーションの書式が不正な場合
     */
    protected Validator<T> getValidator(FormMessage formMessage, Annotation anno) throws ValidatorFormatException {
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
            errors.add(message.getFormMessage(requiredMessageKey, this.label));
            return false;
        }
        try {
            this.value = convert(rawText);
        } catch (ConvertException e) {
            errors.add(message.getFormMessage("validator.invalidFormat", this.label, e.getFormat()));
            return false;
        }
        validate();
        return !hasError();
    }

    /**
     * 入力文字列が空文字の場合にtrueを返す
     * @return rawTextがnullまたは空文字列の場合にtrue
     */
    protected boolean isEmptyValue() {
        return this.rawText == null || this.rawText.isEmpty();
    }

    /**
     * 値の妥当性チェックを行う
     */
    protected void validate() {
        for (Validator<T> v : validators) {
            String error = v.validate(this.label, this.value);
            if (error != null) this.errors.add(error);
        }
    }

    /**
     * テキストを値にコンバートする。
     * @param text テキスト
     * @return 変換された値
     * @throws ConvertException コンバートに失敗した場合
     */
    abstract protected T convert(String text) throws ConvertException;

    /**
     * 値をテキストにコンバートする
     * @param aValue 値
     * @return コンバートされたテキスト
     */
    abstract protected String toString(T aValue);

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

    //CHECKSTYLE:OFF
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
