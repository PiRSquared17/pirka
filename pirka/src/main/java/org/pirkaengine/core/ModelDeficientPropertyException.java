package org.pirkaengine.core;

/**
 * モデルのプロパティが不十分な場合に発生する例外
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ModelDeficientPropertyException extends PirkaException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public ModelDeficientPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public ModelDeficientPropertyException(String message) {
        super(message);
    }
}
