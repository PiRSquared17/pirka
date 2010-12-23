package org.pirkaengine.core.script;

import org.pirkaengine.core.PirkaException;

/**
 * スクリプトエンジンの設定などに問題がある場合にthrowされる例外.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ScriptEngineException extends PirkaException {

    private static final long serialVersionUID = 1L;


    /**
     * コンストラクタ.
     * @param message
     */
    public ScriptEngineException(String message) {
        super(message);
    }
    
    /**
     * コンストラクタ.
     * @param message
     * @param cause
     */
    public ScriptEngineException(String message, Throwable cause) {
        super(message, cause);
    }

}
