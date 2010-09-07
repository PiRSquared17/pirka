package org.pirkaengine.core.expression;

import static org.pirkaengine.core.util.EqualsHelper.*;

import java.io.Serializable;

import org.pirkaengine.core.util.EqualsHelper;


/**
 * 関数クラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 言語 */
    public final String language;
    /** スクリプト */
    public final String script;
    /** 関数名 */
    public final String name;
    /** 関数名（エイリアス） */
    public final String aliasName;
    /** パラメータ */
    public final String[] params;

    /**
     * コンストラクタ.
     * @param language
     * @param script
     * @param name
     * @param params
     */
    public Function(String language, String script, String name, String... params) {
        this.language = language;
        this.script = script;
        this.name = name;
        this.aliasName = "";
        this.params = params;
    }
    
    /**
     * コンストラクタ.
     * @param language
     * @param script
     * @param name
     * @param aliasName
     * @param params
     */
    public Function(String language, String script, String name, String aliasName, String... params) {
        this.language = language;
        this.script = script;
        this.name = name;
        this.aliasName = aliasName;
        this.params = params;
    }
    

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Function[");
        str.append("language=").append(language).append(",");
        str.append("def=").append(name).append("(");
        for (String param : params) {
            str.append(param).append(", ");
        }
        str.delete(str.length() - 2, str.length());
        str.append(")\n");
        str.append(script);
        return str.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Function)) return false;
        Function func = Function.class.cast(obj);
        if (!EqualsHelper.equals(language, func.language)) return false;
        if (!EqualsHelper.equals(script, func.script)) return false;
        if (!EqualsHelper.equals(name, func.name)) return false;
        if (!EqualsHelper.equals(params, func.params)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + strHashCode(language);
        hash = hash * 13 + strHashCode(script);
        hash = hash * 13 + strHashCode(name);
        hash = hash * 13 + strHashCode(params);
        return hash;
    }
}
