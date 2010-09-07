package org.pirkaengine.core.expression;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Java 関数クラス.
 * <p>
 * Javaのクラス１つに対応し、publicで戻り値のある全てのstatic methodを関数として保持する
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class JavaFunction extends Function implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final char SEPARATOR = ':';
    private transient volatile Method method;
    private final String methodClass;
    private final String methodName;
    private final Class<?>[] paramTypes;

    /**
     * Javaのクラス名を指定して、関数オブジェクトの配列を返す
     * @param className
     * @param aliasName 別名、空文字列の場合は無視
     * @return 関数オブジェクトの配列
     * @throws ClassNotFoundException
     */
    public static JavaFunction[] getFunctions(String className, String aliasName) throws ClassNotFoundException {
        ArrayList<Method> methods = new ArrayList<Method>();
        Class<?> funcClass = Class.forName(className);
        for (Method method : funcClass.getMethods()) {
            int mod = method.getModifiers();
            if (!Modifier.isStatic(mod)) continue;
            if (method.getReturnType() == Void.class) continue;
            methods.add(method);
        }
        JavaFunction[] funcs = new JavaFunction[methods.size()];
        for (int i = 0; i < funcs.length; i++) {
            Method method = methods.get(i);
            String[] params = new String[method.getParameterTypes().length];
            for (int j = 0; j < params.length; j++) {
                params[j] = "arg" + j;
            }
            if (aliasName == null || aliasName.length() == 0) {
                funcs[i] = new JavaFunction(funcClass, method, params);
            } else {
                funcs[i] = new JavaFunction(funcClass, aliasName, method, params);
            }
        }
        return funcs;
    }

    JavaFunction(Class<?> funcClass, Method method, String... args) {
        super("java", null, funcClass.getSimpleName() + SEPARATOR + method.getName(), args);
        this.method = method;
        this.methodClass = funcClass.getName();
        this.methodName = method.getName();
        this.paramTypes = method.getParameterTypes();
    }
    
    JavaFunction(Class<?> funcClass, String aliasName, Method method, String... args) {
        super("java", null, funcClass.getSimpleName() + SEPARATOR + method.getName(), aliasName + SEPARATOR + method.getName(), args);
        this.method = method;
        this.methodClass = funcClass.getName();
        this.methodName = method.getName();
        this.paramTypes = method.getParameterTypes();
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException, SecurityException,
            NoSuchMethodException {
        input.defaultReadObject();
        this.method = Class.forName(this.methodClass).getMethod(methodName, paramTypes);
    }

    /**
     * 引数を指定して関数を実行する
     * @param args
     * @return 関数の評価値
     * @throws EvalException
     */
    public Object invoke(Object... args) throws EvalException {
        try {
            return method.invoke(null, args);// static method
        } catch (IllegalArgumentException e) {
            throw new EvalException(e);
        } catch (IllegalAccessException e) {
            // this method is public/static
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            // this method is public/static
            throw new IllegalStateException(e);
        }
    }

}
