package org.pirkaengine.core.template;

/**
 * ブロック要素ノード.
 * <p>
 * このノードはレイアウト時のブロックとして作用するノードとなる
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class BlockNode extends CompositeNode {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノード
     */
    public BlockNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.template.CompositeNode#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof BlockNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.template.CompositeNode#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("BlockNode[");
        str.append("startTagNode=").append(startTagNode);
        str.append(" ,endTagNode=").append(endTagNode);
        str.append(" ,nodes=").append(nodes.length);
        str.append("]");
        return str.toString();
    }
}