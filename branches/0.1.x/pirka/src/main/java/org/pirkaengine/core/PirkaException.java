package org.pirkaengine.core;

/**
 * チェック例外基底クラス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public PirkaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public PirkaException(String message) {
        super(message);
    }

    /**
     * コンストラクタ.
     * @param cause 原因となった例外
     */
    public PirkaException(Throwable cause) {
        super(cause);
    }

}
