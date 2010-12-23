package org.pirkaengine.core.template;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pirkaengine.core.PrkAttribute;
import org.pirkaengine.core.expression.Function;


/**
 * 複合ノード. 
 * <p>
 * とりあえずは、タグのネストに対応する専用
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see StartTagNode
 * @see EndTagNode
 */
public class CompositeNode extends Node {

    private static final long serialVersionUID = 1L;
    final StartTagNode startTagNode;
    final EndTagNode endTagNode;
    final Node[] nodes;

    /**
     * コンストラクタ.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノードの配列
     */
    public CompositeNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        if (startTagNode == null) throw new IllegalArgumentException("startTagNode == null");
        if (nodes == null) throw new IllegalArgumentException("nodes == null");
        if (endTagNode == null) throw new IllegalArgumentException("endTagNode == null");
        this.startTagNode = startTagNode;
        this.endTagNode = endTagNode;
        this.nodes = nodes;
    }

    /**
     * CompositeNodeのファクトリメソッド.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノードの配列
     * @return CompositeNode
     */
    public static CompositeNode newNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        if (startTagNode.attrName.equals(PrkAttribute.CONTENT.name)) {
            return new ContentNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.REPLACE.name)) {
            return new ReplaceNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.WRAP.name)) {
            return new WrapNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.STUB.name)) {
            return new EmptyNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.DEBUG.name)) {
            return new ConditionNode(startTagNode, endTagNode, nodes, ConditionNode.DEBUG_CONDITION);
        } else if (startTagNode.attrName.equals(PrkAttribute.IF.name)) {
            return new ConditionNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.IF_NOT.name)) {
            return new ConditionNode(startTagNode, endTagNode, nodes, ConditionNode.NOT_CONDITION);
        } else if (startTagNode.attrName.equals(PrkAttribute.IF_EMPTY.name)) {
            return new ConditionNode(startTagNode, endTagNode, nodes, ConditionNode.EMPTY_CONDITION);
        } else if (startTagNode.attrName.equals(PrkAttribute.IF_NOT_EMPTY.name)) {
            return new ConditionNode(startTagNode, endTagNode, nodes, ConditionNode.NOT_EMPTY_CONDITION);
        } else if (startTagNode.attrName.equals(PrkAttribute.FOR.name)) {
            return new LoopNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.REPEAT.name)) {
            return new RepeatNode(startTagNode, endTagNode, nodes);
        } else if (startTagNode.attrName.equals(PrkAttribute.BLOCK.name)) {
            return new BlockNode(startTagNode, endTagNode, nodes);
        } else {
            return new CompositeNode(startTagNode, endTagNode, nodes);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        StringBuilder buf = new StringBuilder();
        buf.append(startTagNode.getText(model, functions));
        for (Node node : nodes) {
            buf.append(node.getText(model, functions));
        }
        buf.append(endTagNode.getText(model, functions));
        return buf.toString();
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getExpressions()
     */
    @Override
    public String[] getExpressions() {
        Set<String> expressions = new HashSet<String>(this.startTagNode.prkAttributes.keySet());
        for (Node node : this.nodes) {
            for (String expression : node.getExpressions()) {
                expressions.add(expression);
            }
        }
        return expressions.toArray(new String[expressions.size()]);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CompositeNode)) return false;
        CompositeNode node = CompositeNode.class.cast(obj);
        if (!startTagNode.equals(node.startTagNode)) return false;
        if (!endTagNode.equals(node.endTagNode)) return false;
        if (nodes.length != node.nodes.length) return false;
        for (int i = 0; i < nodes.length; i++) {
            if (!nodes[i].equals(node.nodes[i])) return false;
        }
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + startTagNode.hashCode();
        for (int i = 0; i < nodes.length; i++) {
            hash = hash * 13 + nodes[i].hashCode();
        }
        hash = hash * 13 + endTagNode.hashCode();
        return hash;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("CompositeNode[");
        str.append("startTagNode=").append(startTagNode);
        str.append(" ,endTagNode=").append(endTagNode);
        str.append(" ,nodes=").append(nodes.length);
        str.append("]");
        return str.toString();
    }
}
