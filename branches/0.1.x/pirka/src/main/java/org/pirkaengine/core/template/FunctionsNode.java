package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.JavaFunction;


/**
 * Functionノード
 * @author shuji.w6e
 * @since 0.1.0
 */
public class FunctionsNode extends Node {
    private static final long serialVersionUID = 1L;
    /** JavaFunction (public static) */
    public final JavaFunction[] javaFunctions;
    
    /**
     * コンストラクタ.
     * @param className Javaのクラス完全修飾名
     * @param aliasName 別名、空文字列の場合は無視
     * @throws ClassNotFoundException
     */
    public FunctionsNode(String className, String aliasName) throws ClassNotFoundException {
        javaFunctions = JavaFunction.getFunctions(className, aliasName);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        throw new UnsupportedOperationException();
    }

}
