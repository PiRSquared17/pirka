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
     * @param msg
     */
    public ValidatorFormatException(String msg) {
        super(msg);
    }

    /**
     * Constructor.
     * @param msg
     * @param cause
     */
    public ValidatorFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
