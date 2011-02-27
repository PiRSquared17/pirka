package org.pirkaengine.core.template;

import java.io.Serializable;
import java.util.Map;

/**
 * 開始タグノード.
 * <p>
 * EndTagNodeと共にCompositeNodeで利用する。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see CompositeNode
 * @see EndTagNode
 */
public class StartTagNode extends TagNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ.
     * @param tagText
     * @param attrName
     * @param attrValue
     */
    public StartTagNode(String tagText, String attrName, String attrValue) {
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
    public StartTagNode(String tagText, String attrName, String attrValue, Map<String, String> prkAttributes,
            Map<String, String> pathAttributes, Map<String, String> attributes) {
        super(tagText, attrName, attrValue, prkAttributes, pathAttributes, attributes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof StartTagNode)) return false;
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
