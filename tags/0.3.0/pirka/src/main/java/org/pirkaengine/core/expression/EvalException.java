package org.pirkaengine.core.expression;

import org.pirkaengine.core.PirkaRuntimeException;

/**
 * 式の評価時に発生する例外
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EvalException extends PirkaRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param message メッセージ
     */
    public EvalException(String message) {
        super(message);
    }
    
    /**
     * コンストラクタ
     * @param cause 原因となった例外
     */
    public EvalException(Throwable cause) {
        super(cause);
    }

    /**
     * コンストラクタ
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }
}
