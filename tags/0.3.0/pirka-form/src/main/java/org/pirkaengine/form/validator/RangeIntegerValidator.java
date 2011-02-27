package org.pirkaengine.form.validator;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.RangeInteger;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 整数が特定の範囲であるかを判定するValidator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RangeIntegerValidator extends ValidatorBase<Integer> {

    private final int min;
    private final int max;

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param min 最小値
     * @param max 最大値
     */
    public RangeIntegerValidator(int min, int max) {
        this(min, max, "validator.range");
    }

    /**
     * プレフィックスを指定してインスタンスを生成する.
     * @param min 最小値
     * @param max 最大値
     * @param messageKey メッセージキー
     */
    public RangeIntegerValidator(int min, int max, String messageKey) {
        super(messageKey);
        if (max <= min) throw new ValidatorFormatException("max is less than min: max=" + max + ", min=" + min);
        this.min = min;
        this.max = max;
    }

    /**
     * アノテーションを指定してRangeIntegerValidatorインスタンスを生成する
     * @param message FormMessage
     * @param anno RangeIntegerアノテーション
     * @return RangeIntegerValidator
     */
    public static RangeIntegerValidator create(FormMessage message, RangeInteger anno) {
        int min = anno.min();
        int max = anno.max();
        String msgKey = anno.messageKey();
        RangeIntegerValidator validator;
        if (msgKey == null || msgKey.isEmpty()) {
            validator = new RangeIntegerValidator(min, max);
        } else {
            validator = new RangeIntegerValidator(min, max, msgKey);
        }
        validator.setFormMessage(message);
        return validator;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.ValidatorBase#isValid(java.lang.Object)
     */
    @Override
    protected boolean isValid(Integer value) {
        if (value == null) throw new IllegalArgumentException("value is null.");
        int v = value.intValue();
        return min <= v && v <= max;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.ValidatorBase#getMessageArgs(java.lang.String, java.lang.Object)
     */
    @Override
    protected Object[] getMessageArgs(String name, Integer value) {
        return new Object[] { name, min, max };
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + max;
        result = prime * result + min;
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
        RangeIntegerValidator other = (RangeIntegerValidator) obj;
        if (max != other.max) return false;
        if (min != other.min) return false;
        return true;
    }

}
