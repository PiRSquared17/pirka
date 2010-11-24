package org.pirkaengine.core.script;

import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.JavaFunction;

/**
 * 関数実行インターフェイス.
 * @author shuji
 * @since 1.0
 */
public interface Invoker {

    /** Java の静的メソッド用Invoker */
    static final Invoker JAVA_STATIC_METHOD_INVOKER = new Invoker() {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.pirkaengine.core.script.Invoker#invoke(org.pirkaengine.core.expression
         * .Function, java.lang.Object[])
         */
        @Override
        public Object invoke(Function function, Object[] params) throws ScriptEngineException {
            assert function instanceof JavaFunction;
            return ((JavaFunction) function).invoke(params);
        }
    };

    /**
     * 指定した関数を指定したパラメータで実行する. 
     * @param function 関数
     * @param params パラメータ
     * @return 関数の戻り値
     * @throws ScriptEngineException 関数の実行に失敗した場合
     */
    Object invoke(Function function, Object[] params) throws ScriptEngineException;
}
