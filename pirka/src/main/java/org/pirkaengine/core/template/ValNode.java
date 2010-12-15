package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.StringExpression;

/**
 * Val要素タグノード.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ValNode extends Node {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String value;

    /**
     * コンストラクタ.
     * @param name
     * @param value
     */
    public ValNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.template.StartTagNode#getText(java.util.Map,
     * java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        if (StringExpression.isExpression(value)) {
            model.put(name, ExpressionEngine.getInstance().eval(value, model, functions));
        } else {
            model.put(name, value);
        }
        return "";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ValNode other = (ValNode) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        return true;
    }

}
