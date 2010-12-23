package org.pirkaengine.core.expression;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.pirkaengine.core.script.Invoker;
import org.pirkaengine.core.script.InvokerFactory;
import org.pirkaengine.core.script.ScriptEngineException;


/**
 * 関数エンジンインターフェイス．
 * @author shuji.w6e
 * @since 0.1.0
 */
public class FunctionEngine {

    private final ExpressionEngine expressionEngine;

    /**
     * @param expressionEngine
     */
    public FunctionEngine(ExpressionEngine expressionEngine) {
        if (expressionEngine == null) throw new IllegalArgumentException("expressionEngine is null.");
        this.expressionEngine = expressionEngine;
    }

    /**
     * スクリプトを実行する．
     * @param script スクリプト
     * @param model
     * @param functions 外部定義関数
     * @return スクリプトの戻り値
     * @throws ScriptEngineException 
     */
    public Object eval(String script, Map<String, Object> model, Map<String, Function> functions)
            throws ScriptEngineException {
        if (script == null) throw new IllegalArgumentException("script is null.");
        if (model == null) throw new IllegalArgumentException("model == null");
        if (functions == null) throw new IllegalArgumentException("functions == null");
        String funcName = Functions.getName(script);
        Function function = functions.get(funcName);
        if (function == null) throw new EvalException("function is not found: " + funcName);
        if (function.language == null) throw new EvalException("function.language is null: " + funcName);
        try {
            Invoker invoker = InvokerFactory.getInstance().getInvoker(function);
            Object[] params = createParams(script, model, functions);
            return invoker.invoke(function, params);
        } catch (SecurityException e) {
            throw new EvalException("function:" + function, e);
        } catch (IllegalArgumentException e) {
            throw new EvalException("function:" + function, e);
        } catch (NoSuchMethodException e) {
            throw new EvalException("function:" + function, e);
        } catch (IllegalAccessException e) {
            throw new EvalException("function:" + function, e);
        } catch (InvocationTargetException e) {
            throw new EvalException("function:" + function, e);
        } catch (NoSuchFieldException e) {
            throw new EvalException("function:" + function, e);
        }
    }

    private Object[] createParams(String script, Map<String, Object> model,
            Map<String, Function> functions) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        String[] paramsStr = Functions.getParams(script);
        Object[] params = new Object[paramsStr.length];
        for (int i = 0; i < paramsStr.length; i++) {
            params[i] = expressionEngine.getValue(paramsStr[i], model, functions);
        }
        return params;
    }
}
