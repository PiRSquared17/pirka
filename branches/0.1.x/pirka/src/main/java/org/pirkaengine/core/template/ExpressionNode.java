package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.EqualsHelper.*;

import java.util.Map;

import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.util.EqualsHelper;
import org.pirkaengine.core.util.VariableUtil;


/**
 * 式ノード.
 * <p>
 * 評価式のみで構成されるノード。
 * 評価式は${<式>}または、$_{<式>}で表現され、次のような形でテンプレートに記述される。
 * <code>${some_value}</code>
 * このように記述されたテンプレートは、viewModelからsome_valueの値を評価してレンダリングする．
 * </p>
 * <p>
 * ${}で記述された場合はHTMLのエスケープを行い、$_{}の場合はエスケープを行わない。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ExpressionNode extends Node {
    private static final long serialVersionUID = 1L;
    private final String expression;
    private final boolean htmlEscape;

    /**
     * コンストラクタ.
     * <p>エスケープ処理を行う</p>
     * @param expression ${}または$_{}を含まない評価式
     */
    public ExpressionNode(String expression) {
        this(expression, true);
    }

    /**
     * コンストラクタ.
     * @param expression ${}または$_{}を含まない評価式
     * @param htmlEscape エスケープ処理を行わない場合true
     */
    public ExpressionNode(String expression, boolean htmlEscape) {
        this.expression = expression;
        this.htmlEscape = htmlEscape;
    }

    /**
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        String value = ExpressionEngine.getInstance().eval(this.expression, model, functions);
        if (!this.htmlEscape) return value;
        return VariableUtil.htmlEscape(value);
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getExpressions()
     */
    @Override
    public String[] getExpressions() {
        return new String[] { this.expression };
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExpressionNode)) return false;
        ExpressionNode node = ExpressionNode.class.cast(obj);
        if (!EqualsHelper.equals(expression, node.expression)) return false;
        return boolEquals(htmlEscape, node.htmlEscape);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + strHashCode(expression);
        hash = hash * 13 + boolHashCode(htmlEscape);
        return hash;
    }

}
