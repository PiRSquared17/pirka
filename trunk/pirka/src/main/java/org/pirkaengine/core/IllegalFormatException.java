package org.pirkaengine.core;

/**
 * テンプレートが不正なフォーマットである場合にthrowされる例外.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class IllegalFormatException extends PirkaRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param message エラーメッセージ
     */
    public IllegalFormatException(String message) {
        super(message);
    }

}
