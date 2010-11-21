package org.pirkaengine.core;

/**
 * テンプレートの読み込みに失敗した場合にthrowされる例外.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaLoadException extends PirkaException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public PirkaLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public PirkaLoadException(String message) {
        super(message);
    }

}
