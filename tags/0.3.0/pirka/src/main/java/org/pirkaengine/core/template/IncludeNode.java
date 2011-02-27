package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.Template;
import org.pirkaengine.core.expression.Function;


/**
 * Includeノード.
 * <p>
 * 他のテンプレートファイルをインクルードするノード.
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class IncludeNode extends Node {
    private static final long serialVersionUID = 1L;
    final Template template;

    /**
     * コンストラクタ
     * @param template テンプレートファイル名
     */
    public IncludeNode(Template template) {
        if (template == null) throw new IllegalArgumentException("template == null");
        this.template = template;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return template.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map,
     * java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return template.generate(model).render();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IncludeNode)) return false;
        IncludeNode node = IncludeNode.class.cast(obj);
        return template.equals(node.template);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + template.hashCode();
        return hash;
    }
}
