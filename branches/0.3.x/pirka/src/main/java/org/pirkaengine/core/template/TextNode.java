package org.pirkaengine.core.template;

import java.util.Map;
import java.util.regex.Pattern;

import org.pirkaengine.core.expression.Function;


/**
 * テキストノード.
 * <p>
 * 表示条件や変数などを持たない単純なテキストノード
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class TextNode extends Node {
    private static final long serialVersionUID = 1L;
    static final Pattern PRK_TAG_PATTERN = Pattern.compile("</?prk:.*>");
    final String text;

    /**
     * コンストラクタ
     * @param text 保持するテキスト
     */
    public TextNode(String text) {
        if (text == null) throw new IllegalArgumentException("text == null");
        this.text = text;
    }
    
    /**
     * prk:タグである場合にtrueを返す
     * @return prk:タグである場合にtrue
     */
    public boolean isPrkTag() {
        return PRK_TAG_PATTERN.matcher(text).matches();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return text;
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        return text;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TextNode)) return false;
        TextNode node = TextNode.class.cast(obj);
        return text.equals(node.text);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + text.hashCode();
        return hash;
    }    
}
