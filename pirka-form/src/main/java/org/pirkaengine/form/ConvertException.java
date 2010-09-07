package org.pirkaengine.form;


/**
 * 変換に失敗した場合にthrowされる例外。
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ConvertException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 原因となった例外とメッセージを指定してインスタンスを生成する.
     * @param message
     * @param cause
     */
    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * メッセージを指定してインスタンスを生成する.
     * @param message
     */
    public ConvertException(String message) {
        super(message);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause
     */
    public ConvertException(Throwable cause) {
        super(cause);
    }

}
