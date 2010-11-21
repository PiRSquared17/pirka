package org.pirkaengine.core.template;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.expression.EvalException;
import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;


/**
 * 条件文ノード.
 * <p>
 * このノードは、属性値で評価された結果が真の場合にレンダリングされる.
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see Condition
 */
public class ConditionNode extends CompositeNode {

    /** 標準の条件 */
    public static final Condition DEFAULT_CONDITION = new Condition() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isDisplay(String value, Map<String, Object> model, Map<String, Function> functions) {
            return ExpressionEngine.getInstance().evalBoolean(value, model, functions);
        }

    };
    /** 否定条件 */
    public static final Condition NOT_CONDITION = new Condition() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isDisplay(String value, Map<String, Object> model, Map<String, Function> functions) {
            return !DEFAULT_CONDITION.isDisplay(value, model, functions);
        }

    };
    /** 空要素条件 */
    public static final Condition EMPTY_CONDITION = new Condition() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isDisplay(String value, Map<String, Object> model, Map<String, Function> functions) {
            Object items = model.get(value);
            if (items instanceof Iterable<?>) {
                return !((Iterable<?>) items).iterator().hasNext();
            } else if (items.getClass().isArray()) {
                return Array.getLength(items) == 0;
            }
            throw new EvalException("items is not iteratable.");
        }
    };
    /** 否定条件 */
    public static final Condition NOT_EMPTY_CONDITION = new Condition() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isDisplay(String value, Map<String, Object> model, Map<String, Function> functions) {
            return !EMPTY_CONDITION.isDisplay(value, model, functions);
        }
    };
    /** */
    public static final Condition DEBUG_CONDITION = new Condition() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isDisplay(String value, Map<String, Object> model, Map<String, Function> functions) {
            return PirkaContext.getInstance().isEnableDebug();
        }
    };

    private static final long serialVersionUID = 1L;
    final Condition condition;

    /**
     * コンストラクタ.
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノードの配列
     */
    public ConditionNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        this(startTagNode, endTagNode, nodes, DEFAULT_CONDITION);
    }

    /**
     * コンストラクタ
     * @param startTagNode 開始タグノード
     * @param endTagNode 終了タグノード
     * @param nodes ネストノードの配列
     * @param condition 条件
     */
    public ConditionNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes, Condition condition) {
        super(startTagNode, endTagNode, nodes);
        this.condition = condition;
    }
    
    @Override
    public String[] getExpressions() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(this.startTagNode.attrValue);
        for (Node node : nodes) {
            for (String expression : node.getExpressions()) {
                list.add(expression);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.Node#getText(java.util.Map, java.util.Map)
     */
    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        if (!this.condition.isDisplay(this.startTagNode.attrValue, model, functions)) return "";
        StringBuilder buf = new StringBuilder();
        buf.append(startTagNode.getText(model, functions));
        for (Node node : nodes) {
            buf.append(node.getText(model, functions));
        }
        buf.append(endTagNode.getText(model, functions));
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof ConditionNode)) return false;
        ConditionNode node = ConditionNode.class.cast(obj);
        return this.condition.equals(node.condition);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.template.CompositeNode#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 13 + condition.hashCode();
        return hash;
    }

}
