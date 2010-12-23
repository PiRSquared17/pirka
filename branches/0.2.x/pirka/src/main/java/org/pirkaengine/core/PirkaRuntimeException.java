package org.pirkaengine.core;

/**
 * Pirka実行例外の基底クラス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public PirkaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * @param message メッセージ
     */
    public PirkaRuntimeException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * @param cause 原因となった例外
     */
    public PirkaRuntimeException(Throwable cause) {
        super(cause);
    }
    
}
