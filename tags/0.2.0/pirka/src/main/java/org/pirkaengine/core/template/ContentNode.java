package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;


/**
 * コンテンツを内包するノード。
 * 
 * <p>
 * 内包されたコンテンツは式の評価結果で完全に置換される。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ContentNode extends CompositeNode {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param startTagNode
     * @param endTagNode
     * @param nodes
     */
    public ContentNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
    }

    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        StringBuilder buf = new StringBuilder();
        buf.append(startTagNode.getText(model, functions));
        buf.append(ExpressionEngine.getInstance().eval(this.startTagNode.attrValue, model));
        buf.append(endTagNode.getText(model, functions));
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#getExpressions()
     */
    @Override
    public String[] getExpressions() {
        return new String[] { this.startTagNode.attrValue };
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof ContentNode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
