package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * 評価式でタグの出力を制御するノード。
 * 
 * <p>
 * 評価式が真の場合、タグを出力する。
 * 評価式が偽の場合、タグを出力しない。
 * どちらの場合も、子要素は出力する
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class WrapNode extends ConditionNode {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param startTagNode
     * @param endTagNode
     * @param nodes
     */
    public WrapNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        boolean isDiaplay = this.condition.isDisplay(this.startTagNode.attrValue, model, functions);
        StringBuilder buf = new StringBuilder();
        if (isDiaplay) buf.append(startTagNode.getText(model, functions));
        for (Node node : nodes) {
            buf.append(node.getText(model, functions));
        }
        if (isDiaplay) buf.append(endTagNode.getText(model, functions));
        return buf.toString();
    }
}
