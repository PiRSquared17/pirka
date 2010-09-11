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
     * @param msg メッセージ
     * @param cause 原因となった例外
     */
    public IllegalFormFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * メッセージを指定してインスタンスを生成する.
     * @param msg メッセージ
     */
    public IllegalFormFormatException(String msg) {
        super(msg);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause 原因となった例外
     */
    public IllegalFormFormatException(Throwable cause) {
        super(cause);
    }

}
