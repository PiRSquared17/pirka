package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.Logger.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pirkaengine.core.KeyNotContainsException;
import org.pirkaengine.core.PrkComponent;
import org.pirkaengine.core.Renderer;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.JavaFunction;


/**
 * XHMLテンプレート.
 * <p>
 * TemplateはフラットなNodeのListを保持する。
 * また、各NodeはそれぞれネストしたNodeを子に持つ事ができる。<br />
 * つまり、テンプレートはこのクラスをルートとしたNodeのツリー構造となるが、
 * 完全なComposite構造とはなっていない（ルートが特殊な為）。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class XhtmlTemplate implements Template, Serializable {

    private static final long serialVersionUID = 1L;
    /** Null Value */
    public static final Object NULL_VALUE = new Object();

    /** node list */
    final LinkedList<Node> nodeList = new LinkedList<Node>();
    /** 配列1ist */
    final HashMap<String, LoopNode> loopNodes = new HashMap<String, LoopNode>();
    /** 項目名Set */
    final HashSet<String> itemNames = new HashSet<String>();
    /** function map, key = name  */
    final Map<String, Function> functions = new HashMap<String, Function>();
    /** name of template */
    final String templateName;
    /** timeStamp */
    private long timeStamp = -1;
    /** components */
    final ArrayList<PrkComponent> components = new ArrayList<PrkComponent>();

    /**
    * コンストラクタ.
     * @param templateName テンプレート名
    */
    public XhtmlTemplate(String templateName) {
        this.templateName = templateName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#generate()
     */
    @Override
    public Renderer generate() {
        return this.generate(new HashMap<String, Object>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#generate(java.util.Map)
     */
    @Override
    public Renderer generate(Map<String, Object> viewModel) {
        if (isDebugEnabled()) debug("generate: viewModel=" + viewModel);
        try {
            return new BindTemplate(this, viewModel, this.functions);
        } catch (RuntimeException e) {
            error("Rendering Error: " + this.templateName, e);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#getTimeStamp()
     */
    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#setTimeStamp(long)
     */
    @Override
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#getName()
     */
    @Override
    public String getName() {
        return templateName;
    }

    /**
     * ノードリストの先頭にノードを追加する.
     * @param node 追加するノード
     */
    public void stack(Node node) {
        this.nodeList.addFirst(node);
        if (LoopNode.class.isInstance(node)) {
            LoopNode loopNode = (LoopNode) node;
            this.itemNames.add(loopNode.getItemName());
            this.loopNodes.put(loopNode.getItemsName(), loopNode);
        }
    }

    /**
     * スクリプトの追加
     * @param node ScriptNode
     */
    public void addScript(ScriptNode node) {
        if (node == null) throw new IllegalArgumentException("node is null.");
        if (node.function == null) throw new IllegalArgumentException("node.function is null: " + node);
        if (node.function.name == null) throw new IllegalArgumentException("node.function.name is null: " + node);
        if (isDebugEnabled()) debug("addScript: " + node);
        functions.put(node.function.name, node.function);
    }

    /**
     * 関数の追加
     * @param node FunctionsNode
     */
    public void addFunction(FunctionsNode node) {
        if (node == null) throw new IllegalArgumentException("node is null.");
        if (node.javaFunctions == null) throw new IllegalArgumentException("node.javaFunctions is null: " + node);
        if (isDebugEnabled()) debug("addFunction: " + node);
        for (JavaFunction func : node.javaFunctions) {
            this.functions.put(func.name, func);
            if (func.aliasName != null) this.functions.put(func.aliasName, func);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pirkaengine.core.Template#createViewModel()
     */
    @Override
    public Map<String, Object> createViewModel() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        for (Node node : this.nodeList) {
            for (String nodeExpression : node.getExpressions()) {
                String[] keys = nodeExpression.split("\\.");
                if (this.itemNames.contains(keys[0])) continue; // Loop Items
                setNullValue(model, keys, 0);
            }
            if (LoopNode.class.isInstance(node)) {
                LoopNode loopNode = (LoopNode) node;
                String key = loopNode.getItemsName();
                if (!model.containsKey(key)) model.put(key, new ArrayList<Map<String, Object>>());
            }
        }
        return model;
    }

    private void setNullValue(HashMap<String, Object> model, String[] keys, int index) {
        String key = keys[index];
        Object value = model.get(keys[index]);
        if (index + 1 < keys.length) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> valueModel =
                HashMap.class.isInstance(value) ? (HashMap<String, Object>) value : new HashMap<String, Object>();
            if (value == null) model.put(key, valueModel);
            this.setNullValue(valueModel, keys, index + 1);
        } else {
            if (value == null) model.put(key, NULL_VALUE);
            // END
        }
    }

    @Override
    public Map<String, Object> createArrayItemModel(String arrayKey) throws KeyNotContainsException {
        HashMap<String, Object> model = new HashMap<String, Object>();
        LoopNode node = loopNodes.get(arrayKey);
        if (node == null) throw new KeyNotContainsException("arrayKey: " + arrayKey);
        for (String nodeExpression : node.getExpressions()) {
            String[] keys = nodeExpression.split("\\.");
            if (!node.getItemName().equals(keys[0])) continue; // Loop Items
            if (keys.length == 1) {
                model.put("toString", NULL_VALUE);
            } else {
                setNullValue(model, keys, 1);
            }
        }
        return model;
    }

    /**
     * PrkComponentを追加する
     * @param component PrkComponent
     */
    public void add(PrkComponent component) {
        this.components.add(component);
    }

    /**
     * PrkComponentを取得する
     * @return PrkComponentリスト
     */
    @Override
    public List<PrkComponent> getPrkComponents() {
        return components;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof XhtmlTemplate)) return false;
        XhtmlTemplate tmpl = XhtmlTemplate.class.cast(obj);
        if (this.timeStamp != tmpl.timeStamp) return false;
        if (!this.components.equals(tmpl.components)) return false;
        if (!this.nodeList.equals(tmpl.nodeList)) return false;
        return functions.equals(tmpl.functions);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + (int) (timeStamp ^ timeStamp >>> 32);
        hash = hash * 13 + this.nodeList.hashCode();
        hash = hash * 13 + this.components.hashCode();
        hash = hash * 13 + this.functions.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("nodes=[");
        for (Node node : nodeList) {
            str.append(node).append(", ");
        }
        str.setLength(str.length() - 2);
        str.append("]\n");
        str.append("functions=[").append(functions.keySet()).append("\n");
        str.append("plugins=[").append(components).append("]");
        return str.toString();
    }

}
