package org.pirkaengine.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * コンポーネント.
 * プラグイン等のメタデータを保持する
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PrkComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private final HashMap<String, String> attr = new HashMap<String, String>();

    /**
     * コンストラクタ
     */
    public PrkComponent() {
    }

    /**
     * typeを取得する.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * typeを指定して設定する.
     * @param type 設定するtype
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 属性を設定する
     * @param key
     * @param value
     */
    public void putAttr(String key, String value) {
        this.attr.put(key, value);
    }

    /**
     * キーを指定して属性値を取得する
     * @param key
     * @return 属性値
     */
    public String getAttr(String key) {
        return this.attr.get(key);
    }

    /**
     * 属性のキーセットを取得する
     * @return 属性のキーセット
     */
    public Set<String> getAttrKeys() {
        return this.attr.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrkComponent)) return false;
        PrkComponent comp = PrkComponent.class.cast(obj);
        if (this.type == null && comp.type != null) return false;
        if (this.type != null && !this.type.equals(comp.type)) return false;
        return attr.equals(comp.attr);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 13 + (this.type != null ? this.type.hashCode() : 0);
        hash = hash * 13 + this.attr.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("PrkComponent[");
        str.append("type=").append(type);
        str.append(", attr=[");
        for (String key : attr.keySet()) {
            str.append(key).append("=").append(attr.get(key)).append(", ");
        }
        str.setLength(str.length() - 2);
        str.append("]");
        return str.toString();
    }
}
