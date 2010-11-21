package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * 終了タグノード.
 * <p>
 * 終了タグを表し、レンダリングするだけのシンプルなノード。
 * </p>
 * <p>
 * StartTagと共にCompositeNodeで利用する。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see CompositeNode
 * @see StartTagNode
 */
public class EndTagNode extends TextNode {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param tag
     */
    public EndTagNode(String tag) {
        super(tag);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return super.getText(model, functions);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return false;
        return obj instanceof EndTagNode;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
