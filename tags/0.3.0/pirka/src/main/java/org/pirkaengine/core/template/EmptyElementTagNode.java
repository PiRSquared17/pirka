package org.pirkaengine.core.template;

import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * 空要素タグノード.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EmptyElementTagNode extends TagNode {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param tagText
     * @param attrName
     * @param attrValue
     */
    public EmptyElementTagNode(String tagText, String attrName, String attrValue) {
        super(tagText, attrName, attrValue);
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
    public EmptyElementTagNode(String tagText, String attrName, String attrValue, Map<String, String> prkAttributes,
            Map<String, String> pathAttributes, Map<String, String> attributes) {
        super(tagText, attrName, attrValue, prkAttributes, pathAttributes, attributes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.template.StartTagNode#getText(java.util.Map,
     * java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        StringBuilder text = new StringBuilder(super.getText(model, functions));
        text.insert(text.length() - 1, " /");
        return text.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof EmptyElementTagNode)) return false;
        return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
