package org.pirkaengine.form;

/**
 * Formの例外基底クラス
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaFormRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 原因となった例外とメッセージを指定してインスタンスを生成する.
     * 
     * @param msg メッセージ
     * @param cause 原因となった例外
     */
    public PirkaFormRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * メッセージを指定してインスタンスを生成する.
     * @param msg メッセージ
     */
    public PirkaFormRuntimeException(String msg) {
        super(msg);
    }

    /**
     * 原因となった例外を指定してインスタンスを生成する.
     * @param cause 原因となった例外
     */
    public PirkaFormRuntimeException(Throwable cause) {
        super(cause);
    }

}
