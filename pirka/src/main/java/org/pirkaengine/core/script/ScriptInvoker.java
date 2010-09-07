package org.pirkaengine.core.script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.pirkaengine.core.expression.Function;


/**
 * スクリプト実行クラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class ScriptInvoker implements Invoker {

    private final Map<String, Function> functions;

    /**
     * コンストラクタ。
     */
    public ScriptInvoker() {
        functions = Collections.synchronizedMap(new HashMap<String, Function>());
    }
    
    /**
     * ScriptEngineを取得する
     * @return
     */
    abstract ScriptEngine getEngine();
    
    /**
     * Functionを指定してInvocableを取得する
     * @param function
     * @return
     * @throws ScriptException
     */
    Invocable getInvocable(Function function) throws ScriptException {
        assert function != null;
        assert function.language != null;
        synchronized (this.functions) {
            Function registeredFunc = this.functions.get(function.language);
            if (registeredFunc == null || !registeredFunc.equals(function)) {
                eval(function);
            }
            return (Invocable) getEngine();
        }
    }

    void eval(Function function) throws ScriptException {
        getEngine().eval(function.script);
        functions.put(function.language, function);
    }
}
