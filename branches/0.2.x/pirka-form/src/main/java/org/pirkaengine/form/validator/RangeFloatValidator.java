package org.pirkaengine.form.validator;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.RangeFloat;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 浮動小数点数が特定の範囲であるかを判定する Validator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RangeFloatValidator extends ValidatorBase<Float> {

    private final float min;
    private final float max;

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param min 最小値
     * @param max 最大値
     */
    public RangeFloatValidator(float min, float max) {
        this(min, max, "validator.range");
    }

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param min 最小値
     * @param max 最大値
     * @param messageKey メッセージキー
     */
    public RangeFloatValidator(float min, float max, String messageKey) {
        super(messageKey);
        if (max <= min) throw new ValidatorFormatException("max is less than min: max=" + max + ", min=" + min);
        this.min = min;
        this.max = max;
    }

    /**
     * アノテーションを指定してRangeFloatValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno RangeFloatアノテーション
     * @return RangeFloatValidator
     */
    public static RangeFloatValidator create(FormMessage message, RangeFloat anno) {
        float min = anno.min();
        float max = anno.max();
        String msgKey = anno.messageKey();
        RangeFloatValidator validator;
        if (msgKey == null || msgKey.isEmpty()) {
            validator = new RangeFloatValidator(min, max);
        } else {
            validator = new RangeFloatValidator(min, max, msgKey);
        }
        validator.setFormMessage(message);
        return validator;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.ValidatorBase#isValid(java.lang.Object)
     */
    @Override
    protected boolean isValid(Float value) {
        if (value == null) throw new IllegalArgumentException("value is null.");
        float v = value.floatValue();
        return min <= v && v <= max;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.ValidatorBase#getMessageArgs(java.lang.String, java.lang.Object)
     */
    @Override
    protected Object[] getMessageArgs(String name, Float value) {
        return new Object[] { name, min, max };
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(max);
        result = prime * result + Float.floatToIntBits(min);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RangeFloatValidator other = (RangeFloatValidator) obj;
        if (Float.floatToIntBits(max) != Float.floatToIntBits(other.max)) return false;
        if (Float.floatToIntBits(min) != Float.floatToIntBits(other.min)) return false;
        return true;
    }

}
