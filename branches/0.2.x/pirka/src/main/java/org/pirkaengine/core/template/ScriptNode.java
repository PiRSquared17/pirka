package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.ScriptFunction;


/**
 * スクリプトノード
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ScriptNode extends Node {
    private static final long serialVersionUID = 1L;
    final ScriptFunction function;

    /**
     * コンストラクタ.
     * @param function
     */
    public ScriptNode(ScriptFunction function) {
        if (function == null) throw new IllegalArgumentException("function is null.");
        this.function = function;
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        throw new UnsupportedOperationException();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return function.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ScriptNode)) return false;
        ScriptNode node = ScriptNode.class.cast(obj);
        return function.equals(node.function);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + function.hashCode();
        return hash;
    }

}
