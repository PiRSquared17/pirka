package org.pirkaengine.core;

/**
 * テンプレートが見つからなかった場合にthrowされる例外.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class TemplateNotFoundException extends PirkaException {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public TemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public TemplateNotFoundException(String message) {
        super(message);
    }

}
