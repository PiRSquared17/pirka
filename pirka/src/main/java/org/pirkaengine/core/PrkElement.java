package org.pirkaengine.core;

/**
 * prk要素.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public enum PrkElement {
    /** prk:def */
    DEF("def"),
    /** prk:replace */
    REPLACE("replace"),
    /** prk:for */
    FOR("for"),
    /** prk:component */
    COMPONENT("component"),
    /** prk:param */
    PARAM("param"),
    /** prk:include */
    INCLUDE("include"),
    /** prk:functions */
    FUNCTIONS("functions"),
    ;

    /** 名称 */
    public final String name;
    /** 修飾名 */
    public final String qualifiedName;

    private PrkElement(String name) {
        this.name = name;
        this.qualifiedName = PrkNameSpace.PREFIX + ":" + name;
    }
}
