package org.pirkaengine.core.template;

import org.pirkaengine.core.PirkaRuntimeException;

/**
 * テンプレートの生成に失敗した場合にthrowされる例外.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class TemplateBuildFailedException extends PirkaRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public TemplateBuildFailedException(String message) {
        super(message);
    }

    /**
     * コンストラクタ.
     * @param cause 原因となった例外
     */
    public TemplateBuildFailedException(Throwable cause) {
        super(cause);
    }
}
