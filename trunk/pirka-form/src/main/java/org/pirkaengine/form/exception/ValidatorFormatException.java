package org.pirkaengine.form.exception;

/**
 * Validatorのフォーマットが不正な場合にthrowされる例外。
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ValidatorFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor.
     * @param msg メッセージ
     */
    public ValidatorFormatException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * @param msg メッセージ
     * @param cause 原因となった例外
     */
    public ValidatorFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
