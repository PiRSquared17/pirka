package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;


/**
 * 評価式で要素を置換するノード。
 * 
 * <p>
 * 要素も含め式の評価結果で完全に置換される。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ReplaceNode extends CompositeNode {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param startTagNode
     * @param endTagNode
     * @param nodes
     */
    public ReplaceNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return ExpressionEngine.getInstance().eval(this.startTagNode.attrValue, model);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#getExpressions()
     */
    @Override
    public String[] getExpressions() {
        return new String[] { this.startTagNode.attrValue };
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof ReplaceNode;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
