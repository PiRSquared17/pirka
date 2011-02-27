package org.pirkaengine.core.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.pirkaengine.core.expression.Function;


/**
 * スクリプト実行クラス.
 * <p>
 * クラスローダを分割せず、スレッドも新たに立てずにスクリプトを実行する。
 * トレードオフとして複数の言語を同時に実行することはできない
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class SingleThreadScriptInvoker extends ScriptInvoker {

    private final ScriptEngine engine;
    
    /**
     * コンストラクタ
     * @param language 言語名
     * @throws ScriptEngineException
     */
    public SingleThreadScriptInvoker(String language) throws ScriptEngineException {
        ScriptEngineManager mng = new ScriptEngineManager();
        engine = mng.getEngineByName(language);
        if (engine == null) throw new ScriptEngineException("Cant find ScriptEngineManager: language=" + language);
    }

    @Override
    ScriptEngine getEngine() {
        return engine;
    }

    @Override
    public Object invoke(Function function, Object[] params) throws ScriptEngineException {
        try {
            return getInvocable(function).invokeFunction(function.name, params);
        } catch (ScriptException e) {
            throw new ScriptEngineException("function invoke error.", e);
        } catch (NoSuchMethodException e) {
            throw new ScriptEngineException("function invoke error.", e);
        }
    }

}
