package org.pirkaengine.core.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.PrkAttribute;
import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;

/**
 * タグノード.
 * <p>
 * 開始タグを表しレンダリングする。<br/>
 * 開始タグにはprk:attr.XXなど処理すべき属性を含み、
 * これらの処理を行いつつタグをレンダリングする。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see CompositeNode
 */
public abstract class TagNode extends TextNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Map<String, String> EMPTY = Collections.emptyMap();
    /** 属性名 */
    final String attrName;
    /** 属性値 */
    final String attrValue;
    /** prk:attrで指定された属性：unmodifiable */
    final Map<String, String> prkAttributes;
    /** prk:pathで指定された属性：unmodifiable */
    final Map<String, String> pathAttributes;
    /** 通常の属性：unmodifiable */
    final Map<String, String> attributes;

    /**
     * コンストラクタ.
     * @param tagText
     * @param attrName
     * @param attrValue
     */
    public TagNode(String tagText, String attrName, String attrValue) {
        this(tagText, attrName, attrValue, EMPTY, EMPTY, EMPTY);
    }

    /**
     * コンストラクタ.
     * @param tagText
     * @param attrName
     * @param attrValue
     * @param prkAttributes
     * @param pathAttributes 
     * @param attributes 
     */
    public TagNode(String tagText, String attrName, String attrValue, Map<String, String> prkAttributes, Map<String, String> pathAttributes, Map<String, String> attributes) {
        super(removeAllAttrs(tagText));
        if (attrName == null) throw new IllegalArgumentException("attrName == null");
        if (attrValue == null) throw new IllegalArgumentException("attrValue == null");
        if (prkAttributes == null) throw new IllegalArgumentException("attributes == null");
        this.attrName = attrName;
        this.attrValue = attrValue;
        this.prkAttributes = Collections.unmodifiableMap(prkAttributes);
        this.pathAttributes = Collections.unmodifiableMap(pathAttributes);
        this.attributes = Collections.unmodifiableMap(attributes);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.TextNode#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        String text = super.getText(model, functions);
        if (prkAttributes.isEmpty() && attributes.isEmpty()) return text;
        StringBuilder buf = new StringBuilder(text);
        buf.setLength(buf.length() - 1);
        Set<String> attrs = new HashSet<String>();
        // prk:attr.selected.when を prk:attr.selectedより先に処理するためソート
        ArrayList<String> keys = new ArrayList<String>(this.prkAttributes.keySet());
        Collections.sort(keys);
        Collections.reverse(keys);
        for (String key : keys) {
            String attributeName = key.substring(PrkAttribute.ATTR.name.length() + 1);
            if (!attributeName.endsWith(".when")) {
                if (attrs.contains(attributeName)) continue;
                String value = ExpressionEngine.getInstance().eval(prkAttributes.get(key), model, functions);
                buf.append(' ').append(attributeName).append("=\"").append(value).append('"');
                attrs.add(attributeName);
            } else { // when
                if (!ExpressionEngine.getInstance().evalBoolean(prkAttributes.get(key), model, functions)) {
                    // 表示しないので属性名だけ登録
                    attrs.add(attributeName.substring(0, attributeName.lastIndexOf('.')));
                }
            }
        }
        for (String key : this.pathAttributes.keySet()) {
            StringBuilder path = new StringBuilder(this.pathAttributes.get(key));
            key = key.substring(PrkAttribute.PATH.name.length() + 1);
            if (path.charAt(0) == '/') path.deleteCharAt(0);
            path.insert(0, PirkaContext.getInstance().getAppRoot());
            attrs.add(key);
            buf.append(' ').append(key).append("=\"").append(path).append('"');
        }
        for (String key : this.attributes.keySet()) {
            if (attrs.contains(key)) continue;
            buf.append(' ').append(key).append("=\"").append(attributes.get(key)).append('"');
        }
        buf.append('>');
        return buf.toString();
    }
    
    /** 属性部分をすべて除去する */
    private static String removeAllAttrs(String tagText) {
        StringBuilder buf = new StringBuilder(tagText);
        if (buf.indexOf("=") < 0) return tagText;
        assert buf.charAt(0) != ' '; // <x
        assert buf.charAt(1) != ' '; // <x
        int index = 2;
        while (buf.charAt(index) != ' ') {
            index++;
        }
        buf.delete(index, buf.length() - 1);
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof TagNode)) return false;
        StartTagNode node = StartTagNode.class.cast(obj);
        if (!prkAttributes.equals(node.prkAttributes)) return false;
        if (!attrName.equals(node.attrName)) return false;
        return attrValue.equals(node.attrValue);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 13 + attrName.hashCode();
        hash = hash * 13 + attrValue.hashCode();
        hash = hash * 13 + prkAttributes.hashCode();
        return hash;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.text + ":" + attrName + "=" + attrValue + ":" + this.prkAttributes;
    }
}
