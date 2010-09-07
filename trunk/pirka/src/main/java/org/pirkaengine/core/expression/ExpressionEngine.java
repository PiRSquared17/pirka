package org.pirkaengine.core.expression;

import static org.pirkaengine.core.util.Logger.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.pirkaengine.core.script.ScriptEngineException;
import org.pirkaengine.core.util.VariableUtil;


/**
 * 評価式エンジン.
 * @author shuji
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ExpressionEngine {

    private static final Map<String, Object> EMPTY_MODEL = new HashMap<String, Object>();
    private static final Map<String, Function> EMPTY_FUNCTIONS = new HashMap<String, Function>();

    private static final ExpressionEngine INSTANCE = new ExpressionEngine();

    /**
     * シングルトンインスタンスを取得する.
     * @return インスタンス
     */
    public static ExpressionEngine getInstance() {
        return INSTANCE;
    }

    private final FunctionEngine functionEngine;

    /**
     * コンストラクタ.
     */
    public ExpressionEngine() {
        functionEngine = new FunctionEngine(this);
    }

    /**
     * 式を評価して評価結果を文字列として返す.
     * @param expression
     * @return 評価後の文字列
     */
    public String eval(String expression) {
        return eval(expression, EMPTY_MODEL, EMPTY_FUNCTIONS);
    }

    /**
     * 式を評価して評価結果を真偽値として返す.
     * @param expression 評価式
     * @return 評価後の真偽値
     */
    public boolean evalBoolean(String expression) {
        return evalBoolean(expression, EMPTY_MODEL, EMPTY_FUNCTIONS);
    }

    /**
     * 式を評価して評価結果を真偽値として返す.
     * @param expression 評価式
     * @param model モデル
     * @return 評価後の真偽値
     */
    public boolean evalBoolean(String expression, Map<String, Object> model) {
        return evalBoolean(expression, model, EMPTY_FUNCTIONS);
    }

    /**
     * 式を評価して評価結果を真偽値として返す.
     * @param expression 評価式
     * @param model モデル
     * @param functions 関数セット
     * @return 評価後の真偽値
     */
    public boolean evalBoolean(String expression, Map<String, Object> model, Map<String, Function> functions) {
        if (isDebugEnabled()) debug("evalBoolean: " + expression + ",model= " + model + ",functions: " + functions);
        Object value = getValue(expression, model, functions);
        if (value == null) return false;
        if (value.equals(Boolean.TRUE)) return Boolean.TRUE;
        if (value.equals(Boolean.FALSE)) return Boolean.FALSE;
        if (value instanceof String) {
            String str = (String) value;
            if (str.length() == 0) return false;
            if (str.toLowerCase().equals("false")) return false;
        } else if (Number.class.isInstance(value)) {
            if (Number.class.cast(value).intValue() == 0) return false;
        } else if (Collection.class.isInstance(value)) {
            if (Collection.class.cast(value).isEmpty()) return false;
        } else if (value.getClass().isArray()) {
            if (((Object[]) value).length == 0) return false;
        }
        String valueStr = value.toString().toLowerCase();
        if (valueStr.equals("false") || valueStr.equals("0")) {
            return false;
        }
        return true;
    }

    /**
     * 式を評価して評価結果を文字列として返す.
     * <p>
     * 式の評価ルールは以下の通り.<br />
     * 1. 中括弧{}を含む場合、文字列式として評価する
     * 2. 数値として評価可能な場合、数値として評価する
     * 3. 括弧()を含む場合、関数として評価する
     * 4. ViweModelに定義された値として評価する
     * </p>
     * @param expression
     * @param model
     * @return 評価後の文字列
     */
    public String eval(String expression, Map<String, Object> model) {
        return eval(expression, model, EMPTY_FUNCTIONS);
    }

    /**
     * 式を評価して評価結果を文字列として返す.
     * @param expression
     * @param model
     * @param functions
     * @return 評価後の文字列
     */
    public String eval(String expression, Map<String, Object> model, Map<String, Function> functions) {
        if (isDebugEnabled()) debug("eval: " + expression + ",model= " + model + ",functions: " + functions);
        Object value = getValue(expression, model, functions);
        if (value == null) return "";
        if (value instanceof Map<?, ?>) {
            value = ((Map<?, ?>) value).get("toString");
        }
        return value.toString();
    }

    /**
     * 式を評価して評価結果をオブジェクトとして返す.
     * @param expression
     * @param model
     * @param functions
     * @return 評価後のオブジェクト, キーが存在しない場合はnull
     */
    public Object getValue(String expression, Map<String, Object> model, Map<String, Function> functions) {
        if (expression == null) throw new IllegalArgumentException("expression is null.");
        if (model == null) throw new IllegalArgumentException("model is null.");
        if (expression.isEmpty()) return "";
        try {
            // 文字列式
            StringExpression se = StringExpression.createOrNull(expression);
            if (se == null) {
                if (expression.contains("==")) {
                    // ==
                    String[] expressions = expression.split("==");
                    Object left = this.eval(expressions[0], model, functions);
                    Object right = this.eval(expressions[1], model, functions);
                    if (left == null && right == null) return Boolean.TRUE;
                    if (left == null && right != null) return Boolean.FALSE;
                    return left.equals(right);
                } else if (expression.contains("!=")) {
                    // !=
                    String[] expressions = expression.split("!=");
                    Object left = this.eval(expressions[0], model, functions);
                    Object right = this.eval(expressions[1], model, functions);
                    if (left == null && right == null) return Boolean.FALSE;
                    if (left == null && right != null) return Boolean.TRUE;
                    return !left.equals(right);
                } else {
                    // それ以外
                    Object value = this.getValue(model, functions, ExpressionUtil.split(expression));
                    return value;
                }
            } else {
                Object[] values = new Object[se.expressions.length];
                for (int i = 0; i < values.length; i++) {
                    values[i] = getValue(se.expressions[i], model, functions);
                }
                return se.format(values);
            }
        } catch (SecurityException e) {
            throw new EvalException(e);
        } catch (NoSuchMethodException e) {
            throw new EvalException(e);
        } catch (IllegalArgumentException e) {
            throw new EvalException(e);
        } catch (IllegalAccessException e) {
            throw new EvalException(e);
        } catch (InvocationTargetException e) {
            throw new EvalException(e);
        } catch (NoSuchFieldException e) {
            throw new EvalException(e);
        } catch (ScriptEngineException e) { // TODO 上にあげるかどうか微妙？
            throw new EvalException(e);
        }
    }

    Object getValue(Map<String, Object> model, Map<String, Function> functions, String... keys)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchFieldException, ScriptEngineException {
        assert 0 < keys.length;
        if (keys[0].charAt(0) == '#') return keys[0].substring(1);
        Number num = VariableUtil.toNumber(keys[0]);
        if (num != null) return num;
        if (keys[0].matches(".+\\(.*\\)")) { // Function
            return functionEngine.eval(keys[0], model, functions);
        }
        Object value = model.get(keys[0]);
        if (value == null) return null;
        if (keys.length == 1) return value;
        for (int i = 1; i < keys.length; i++) {
            value = getValue(value, keys[i]);
        }
        return value;
    }

    // TODO クラス情報のキャッシュ
    private Object getValue(Object value, String key) throws EvalException {
        if (Map.class.isInstance(value)) {
            Map<?, ?> map = Map.class.cast(value);
            if (!map.containsKey(key)) throw new EvalException("not found in Map: " + key);
            return map.get(key);
        }
        Class<?> clazz = value.getClass();
        try {
            try {
                Field field = clazz.getField(key);
                if (Modifier.isPublic(field.getModifiers())) {
                    // return returnEmptyStringIfNull(field.get(value));
                    return field.get(value);
                }
            } catch (NoSuchFieldException e) {
                // do nothing
            }
            try {
                Method method = clazz.getMethod(key);
                if (method.getReturnType() != Void.class && Modifier.isPublic(method.getModifiers())) {
                    // return returnEmptyStringIfNull(method.invoke(value));
                    return method.invoke(value);
                }
            } catch (NoSuchMethodException e) {
                // do nothing
            }
            try {
                Method method = clazz.getMethod(getGetter(key));
                if (method.getReturnType() != Void.class && Modifier.isPublic(method.getModifiers())) {
                    // return returnEmptyStringIfNull(method.invoke(value));
                    return method.invoke(value);
                }
            } catch (NoSuchMethodException e) {
                // do nothing
            }
            StringBuilder info = new StringBuilder();
            info.append(clazz).append("¥n");
            Method[] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                info.append(methods[i]).append("¥n");
            }
            throw new EvalException("cant access key:" + key + ", value=" + value + "," + info); // TODO
            // error
        } catch (SecurityException e) {
            throw new EvalException("cant access key:" + key + ", value=" + value, e);
        } catch (IllegalArgumentException e) {
            throw new EvalException("cant access key:" + key + ", value=" + value, e);
        } catch (IllegalAccessException e) {
            throw new EvalException("cant access key:" + key + ", value=" + value, e);
        } catch (InvocationTargetException e) {
            throw new EvalException("cant access key:" + key + ", value=" + value, e);
        }
    }

    private Object returnEmptyStringIfNull(Object value) {
        return value != null ? value : "";
    }

    private String getGetter(String key) {
        StringBuilder buf = new StringBuilder(key);
        char c = Character.toUpperCase(buf.charAt(0));
        buf.deleteCharAt(0);
        buf.insert(0, c);
        buf.insert(0, "get");
        return buf.toString();
    }

}
