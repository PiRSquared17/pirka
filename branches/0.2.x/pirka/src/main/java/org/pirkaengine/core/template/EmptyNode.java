package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;

/**
 * 空要素ノード.
 * <p>
 * このノードはレンダリング時に出力されない空の要素となる。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EmptyNode extends CompositeNode {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノード
     */
    public EmptyNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
    }

    /**
     * 空文字列を返す.
     * @return 空文字列
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return "";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EmptyNode";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof EmptyNode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
