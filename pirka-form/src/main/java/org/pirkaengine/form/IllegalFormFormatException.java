package org.pirkaengine.form;


/**
 * Formのフォーマットエラーに関する例外
 * @author shuji.w6e
 * @since 0.1.0
 */
public class IllegalFormFormatException extends PirkaFormRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 原因となった例外とメッセージを指定してインスタンスを生成する.
     * @param message
     * @param cause
     */
    public IllegalFormFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * メッセージを指定してインスタンスを生成する.
     * @param message
     */
    public IllegalFormFormatException(String message) {
        super(message);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause
     */
    public IllegalFormFormatException(Throwable cause) {
        super(cause);
    }

}
