package org.pirkaengine.core;

/**
 * レンダリング時に発生する例外
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RenderException extends PirkaRuntimeException {
    private static final long serialVersionUID = -1;

    /**
     * コンストラクタ
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * @param message メッセージ
     */
    public RenderException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * @param cause 原因となった例外
     */
    public RenderException(Throwable cause) {
        super(cause);
    }

}
