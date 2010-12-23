package org.pirkaengine.core;

/**
 * テンプレート内にキーが存在しない場合にthrowされる例外.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class KeyNotContainsException extends PirkaRuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param msg エラーメッセージ
     */
    public KeyNotContainsException(String msg) {
        super(msg);
    }

}
