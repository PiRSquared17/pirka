package org.pirkaengine.core.serialize;

import org.pirkaengine.core.PirkaException;

/**
 * シリアライズ、デシリアライズ時に発生する例外
 * @author shuji.w6e
 * @since 0.1.0
 */
public class SerializeException extends PirkaException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public SerializeException(String message) {
        super(message);
    }

    /**
     * コンストラクタ.
     * @param cause 原因となった例外
     */
    public SerializeException(Throwable cause) {
        super(cause);
    }

}
