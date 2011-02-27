package org.pirkaengine.core.expression;

import org.pirkaengine.core.IllegalFormatException;

/**
 * Script 関数.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ScriptFunction extends Function {

    /**
     * スクリプトを解析してFunctionオブジェクトを作成する.
     * @param language
     * @param function
     * @param script
     * @return Functionインスタンス
     */
    public static ScriptFunction create(String language, String function, String script) {
        int idx = function.indexOf('(');
        if (idx < 0) {
            throw new IllegalFormatException("function format: foo() or foo(hoge), but :" + function);
        }
        String name = Functions.getName(function);
        String[] params = Functions.getParams(function);
        if (params.length == 0) {
            return new ScriptFunction(language, script, name);
        } else {
            return new ScriptFunction(language, script, name, params);
        }
    }

    /**
     * コンストラクタ.
     * @param language
     * @param script
     * @param name
     * @param params
     */
    public ScriptFunction(String language, String script, String name, String... params) {
        super(language, script, name, params);
    }

    private static final long serialVersionUID = 1L;

}
