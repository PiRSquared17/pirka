package org.pirkaengine.core.parser;

import org.pirkaengine.core.PirkaRuntimeException;

/**
 * 改行コードが混在している場合にthrowされる例外.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class UnkownLineBreakException extends PirkaRuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param message メッセージ
     */
    public UnkownLineBreakException(String message) {
        super(message);
    }

}
