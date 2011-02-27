package org.pirkaengine.core.expression;

import java.util.HashMap;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.expression.EvalException;
import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.FunctionEngine;
import org.pirkaengine.core.script.ScriptEngineException;

public class FunctionEngineTest {

    FunctionEngine target;
    String script;
    Map<String, Object> model;
    Map<String, Function> functions;

    @Before
    public void setup() {
        target = new FunctionEngine(ExpressionEngine.getInstance());
        script = "foo(hoge)";
        model = new HashMap<String, Object>();
        functions = new HashMap<String, Function>();
        functions.put("bar", new Function(null, "return 0;", "bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void FunctionEngine_null() {
        new FunctionEngine(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eval_null() throws ScriptEngineException {
        target.eval(null, model, functions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eval_null_model() throws ScriptEngineException {
        target.eval(script, null, functions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eval_null_functions() throws ScriptEngineException {
        target.eval(script, model, null);
    }

    @Test(expected = EvalException.class)
    public void eval_function_not_found() throws ScriptEngineException {
        target.eval("poo(hoge)", model, functions);
    }

}
