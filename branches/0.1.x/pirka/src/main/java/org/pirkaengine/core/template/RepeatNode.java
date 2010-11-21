package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.EqualsHelper.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.util.EqualsHelper;


/**
 * 繰り返し要素ノード.
 * <p>
 * このノードは、属性値で評価された回数、繰り返してレンダリングされる.
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RepeatNode extends CompositeNode {
    private static final long serialVersionUID = 1L;

    /** 反復回数パラメータ名 */
    private final String param;

    /**
     * コンストラクタ.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノード
     */
    public RepeatNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
        this.param = startTagNode.attrValue;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getExpressions()
     */
    @Override
    public String[] getExpressions() {
        Set<String> expressions = new HashSet<String>();
        expressions.add(startTagNode.attrValue);
        for (Node node : this.nodes) {
            for (String expression : node.getExpressions()) {
                expressions.add(expression);
            }
        }
        return expressions.toArray(new String[expressions.size()]);
    }

    private int getRepeatCount(Map<String, Object> model, Map<String, Function> functions) {
        Object num = ExpressionEngine.getInstance().getValue(this.param, model, functions);
        if (num instanceof Number) {
            return ((Number) num).intValue();
        }
        throw new RenderException("not repeat object: " + this.param);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        int repeatCount = getRepeatCount(model, functions);
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < repeatCount; i++) {
            buf.append(startTagNode.getText(model, functions));
            for (Node node : nodes) {
                buf.append(node.getText(model, functions));
            }
            buf.append(endTagNode.getText(model, functions));
        }
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof RepeatNode)) return false;
        RepeatNode node = RepeatNode.class.cast(obj);
        if (!EqualsHelper.equals(param, node.param)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 13 + strHashCode(param);
        return hash;
    }

}
