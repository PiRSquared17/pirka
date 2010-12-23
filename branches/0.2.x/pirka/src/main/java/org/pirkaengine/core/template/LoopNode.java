package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.EqualsHelper.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.expression.ExpressionEngine;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.util.EqualsHelper;


/**
 * 繰り返し処理を行うノード。 
 * <p> 内包されたノードを繰り返し評価する。 <br/>
 * <code>
 *   prk:for="item in items"
 * </code> 
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class LoopNode extends CompositeNode {
    private static final long serialVersionUID = 1L;
    /** インデックス名 */
    private final String indexName;
    /** 項目名 */
    private final String itemName;
    /** 配列名 */
    private final String itemsName;

    /**
     * コンストラクタ.
     * @param startTagNode
     * @param endTagNode
     * @param nodes
     */
    public LoopNode(StartTagNode startTagNode, EndTagNode endTagNode, Node[] nodes) {
        super(startTagNode, endTagNode, nodes);
        String[] params = startTagNode.attrValue.split("(\\s|,)+");
        if (params.length == 3) {
            indexName = null;
            itemName = params[0];
            checkIn(params, 1);
            itemsName = params[2];
        } else if (params.length == 4) {
            indexName = params[0];
            itemName = params[1];
            checkIn(params, 2);
            itemsName = params[3];
        } else {
            throw new RenderException("illegal format for prk:for :" + startTagNode.attrValue);
        }
    }

    /**
     * 配列名を取得する.
     * @return 配列名
     */
    public String getItemsName() {
        return this.itemsName;
    }

    /**
     * 項目名を取得する.
     * @return 項目名
     */
    public String getItemName() {
        return this.itemName;
    }

    private void checkIn(String[] params, int index) {
        if (!params[index].equals("in")) throw new RenderException("Illegal expression: " + startTagNode.attrValue);
    }

    private Iterable<?> getIterable(Map<String, Object> model, Map<String, Function> functions) {
        Object iterable = ExpressionEngine.getInstance().getValue(this.itemsName, model, functions);
        if (iterable instanceof Iterable<?>) {
            return (Iterable<?>) iterable;
        } else if (iterable.getClass().isArray()) {
            return Arrays.asList((Object[]) iterable);
        }
        throw new RenderException("not iteratable object: " + iterable);
    }
    
    @Override
    public String[] getExpressions() {
        Set<String> expressions = new HashSet<String>();
        for (Node node : this.nodes) {
            for (String expression : node.getExpressions()) {
                expressions.add(expression);
            }
        }
        return expressions.toArray(new String[expressions.size()]);
    }

    @Override
    public String getText(Map<String, Object> model, Map<String, Function> functions) {
        if (model.containsKey(itemName)) throw new RenderException("duplicated key: " + itemName);
        if (indexName != null && model.containsKey(indexName)) throw new RenderException("duplicated key: " + indexName);
        StringBuilder buf = new StringBuilder();
        int index = 0;
        for (Object item : getIterable(model, functions)) {
            model.put(itemName, item);
            if (!startTagNode.isPrkTag()) buf.append(startTagNode.getText(model, functions));
            if (indexName != null) model.put(indexName, index);
            for (Node node : nodes) {
                buf.append(node.getText(model, functions));
            }
            if (!endTagNode.isPrkTag()) buf.append(endTagNode.getText(model, functions));
            index++;
        }
        model.remove(itemName);
        if (indexName != null) model.remove(indexName);
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof LoopNode)) return false;
        LoopNode node = LoopNode.class.cast(obj);
        if (!EqualsHelper.equals(indexName, node.indexName)) return false;
        if (!EqualsHelper.equals(itemName, node.itemName)) return false;
        if (!EqualsHelper.equals(itemsName, node.itemsName)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 13 + strHashCode(indexName);
        hash = hash * 13 + strHashCode(itemName);
        hash = hash * 13 + strHashCode(itemsName);
        return hash;
    }

}
