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
     * @param msg メッセージ
     * @param cause 原因となった例外
     */
    public ConvertException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * メッセージを指定してインスタンスを生成する.
     * @param msg メッセージ
     */
    public ConvertException(String msg) {
        super(msg);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause 原因となった例外
     */
    public ConvertException(Throwable cause) {
        super(cause);
    }

}
