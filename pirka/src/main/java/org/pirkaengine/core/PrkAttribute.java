package org.pirkaengine.core;

/**
 * prk属性.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public enum PrkAttribute {
    
    /** prk:content */
    CONTENT("content"),
    /** prk:attr */
    ATTR("attr"),
    /** prk:path */
    PATH("path"),
    /** prk:replace */
    REPLACE("replace"),
    /** prk:wrap */
    WRAP("wrap"),
    /** prk:stub */
    STUB("stub"),
    /** prk:debug */
    DEBUG("debug"),
    /** prk:if */
    IF("if"),
    /** prk:if.not */
    IF_NOT("if.not"),
    /** prk:if.empty */
    IF_EMPTY("if.empty"),
    /** prk:if.not.empty */
    IF_NOT_EMPTY("if.not.empty"),
    /** prk:for */
    FOR("for"),
    /** prk:repeat */
    REPEAT("repeat"),
    /** prk:extends */
    EXTENDS("extends"),
    /** prk:block */
    BLOCK("block"),
    ;

    /** 名称 */
    public final String name;
    /** 修飾名 */
    public final String qualifiedName;

    private PrkAttribute(String name) {
        this.name = name;
        this.qualifiedName = PrkNameSpace.PREFIX + ":" + name;
    }
}
