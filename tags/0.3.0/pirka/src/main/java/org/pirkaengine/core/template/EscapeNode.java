package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * Escapeノードクラス．
 * <p>
 * 先頭の文字を無視し、２文字目以降をレンダリングする.
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EscapeNode extends TextNode {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param str
     */
    public EscapeNode(String str) {
        super(str);
        if (str.length() < 2) throw new IllegalArgumentException("str length < 2 :" + str);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return text.substring(1);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return obj instanceof EscapeNode;
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
