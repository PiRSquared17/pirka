package org.pirkaengine.core.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * パース時の断片情報を保持する.
 * このクラスのインスタンスは不変オブジェクト．
 * <p>
 * prkKeyとprkValueは、独立した専用のタグを記述した場合のフラグメントとして設定される。<br/>
 * 例えば、<code>&lt;prk:replace value="name" /></code>である場合、
 * prkKeyはreplaceとなり、prkValueはnameとなる。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class Fragment {

    /** オフセット */
    public final int offset;
    /** 種別 */
    public final Type type;
    /** prk:key */
    public final String prkKey;
    /** prk:value */
    public final String prkValue;
    /** 属性 */
    private final List<Attribute> attributes;
    /** prk属性 */
    private final List<Attribute> prkAttributes;
    /** path属性 */
    private final List<Attribute> pathAttributes;

    /**
     * フラグメントビルダ
     * @author shuji
     * @since 0.1
     */
    public static class Builder {
        private int offset = -1;
        private Type type = null;
        private String key = null;
        private String value = null;
        private List<Attribute> attrs = Collections.emptyList();;
        private List<Attribute> prkAttrs = Collections.emptyList();;
        private List<Attribute> pathAttrs = Collections.emptyList();;

        private Builder(int offset, Type type) {
            this.offset = offset;
            this.type = type;
        }

        /**
         * 属性のキーと値を設定する
         * @param key
         * @param value
         * @return ビルダ
         */
        public Builder append(String aKey, String aValue) {
            this.key = aKey;
            this.value = aValue;
            return this;
        }

        /**
         * 属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendAttrs(List<Attribute> attributes) {
            this.attrs = attributes;
            return this;
        }

        /**
         * 属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendAttrs(Attribute... attributes) {
            this.attrs = toList(attributes);
            return this;
        }

        /**
         * prk属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendprkAttrs(List<Attribute> attributes) {
            this.prkAttrs = attributes;
            return this;
        }

        /**
         * prk属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendPrkAttrs(Attribute... attributes) {
            this.prkAttrs = toList(attributes);
            return this;
        }

        /**
         * path属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendPathAttrs(List<Attribute> attributes) {
            this.pathAttrs = attributes;
            return this;
        }

        /**
         * path属性を設定する
         * @param attrs
         * @return ビルダ
         */
        public Builder appendPathAttrs(Attribute... attributes) {
            this.pathAttrs = toList(attributes);
            return this;
        }

        /**
         * fragmentの属性をコピーする
         * @param fragment
         * @return ビルダ
         */
        public Builder copy(Fragment fragment) {
            appendAttrs(fragment.attributes);
            appendprkAttrs(fragment.prkAttributes);
            appendPathAttrs(fragment.pathAttributes);
            return this;
        }

        /**
         * フラグメントをビルドする
         * @return フラグメント
         */
        public Fragment build() {
            return new Fragment(offset, type, key, value, attrs, prkAttrs, pathAttrs);
        }

        private List<Attribute> toList(Attribute... attributes) {
            ArrayList<Attribute> list = new ArrayList<Attribute>();
            for (Attribute attr : attributes) {
                list.add(attr);
            }
            return list;
        }
    }

    /**
     * ビルダを作成する
     * @param offset
     * @param type
     * @return Builder
     */
    public static Builder create(int offset, Type type) {
        return new Builder(offset, type);
    }

    /**
     * コンストラクタ
     * @param offset オフセット
     * @param type 種別
     * @param key prk:key
     * @param value prk:value
     * @param attrs 属性
     * @param prkAttrs prk属性
     * @param pathAttrs path属性
     */
    public Fragment(int offset, Type type, String key, String value, List<Attribute> attrs, List<Attribute> prkAttrs,
            List<Attribute> pathAttrs) {
        this.offset = offset;
        this.type = type;
        this.prkKey = key;
        this.prkValue = value;
        this.attributes = new ArrayList<Attribute>(attrs);
        this.prkAttributes = new ArrayList<Attribute>(prkAttrs);
        this.pathAttributes = new ArrayList<Attribute>(pathAttrs);
    }

    /**
     * prk属性一覧取得
     * @return 属性一覧
     */
    public Map<String, String> prkAttributes() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Attribute attr : this.prkAttributes) {
            map.put(attr.key, attr.value);
        }
        return map;
    }

    /**
     * path属性一覧取得
     * @return path属性一覧
     */
    public Map<String, String> pathAttributes() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Attribute attr : this.pathAttributes) {
            map.put(attr.key, attr.value);
        }
        return map;
    }

    /**
     * 属性一覧取得
     * @return 属性一覧
     */
    public Map<String, String> attributes() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Attribute attr : this.attributes) {
            map.put(attr.key, attr.value);
        }
        return map;
    }

    /**
     * 属性の配列を防御的コピーで取得する
     * @return 属性の配列
     */
    public Attribute[] attributeArray() {
        return this.prkAttributes.toArray(new Attribute[prkAttributes.size()]);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(offset).append(":").append(type).append(":").append(prkKey).append("=").append(prkValue).append("[");
        for (Attribute attr : this.attributes) {
            str.append(attr).append(',');
        }
        for (Attribute attr : this.prkAttributes) {
            str.append("prk:").append(attr).append(',');
        }
        for (Attribute attr : this.pathAttributes) {
            str.append("path:").append(attr).append(',');
        }
        str.append("]");
        return str.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fragment)) return false;
        Fragment flg = (Fragment) obj;
        if (flg.offset != this.offset) return false;
        if (flg.type != this.type) return false;
        if (!(flg.prkKey != null ? flg.prkKey.equals(this.prkKey) : this.prkKey == null)) return false;
        if (!(flg.prkValue != null ? flg.prkValue.equals(this.prkValue) : this.prkValue == null)) return false;
        if (!flg.attributes.equals(this.attributes)) return false;
        if (!flg.pathAttributes.equals(this.pathAttributes)) return false;
        return flg.prkAttributes.equals(this.prkAttributes);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 7 + this.offset;
        hash = hash * 7 + this.type.hashCode();
        hash = hash * 7 + (this.prkKey != null ? this.prkKey.hashCode() : 0);
        hash = hash * 7 + (this.prkValue != null ? this.prkValue.hashCode() : 0);
        hash = hash * 7 + this.attributes.hashCode();
        hash = hash * 7 + this.prkAttributes.hashCode();
        hash = hash * 7 + this.pathAttributes.hashCode();
        return hash;
    }

    /**
     * フラグメント種別.
     * @author shuji
     * @since 0.1
     */
    public static enum Type {
        /** テキスト */
        TEXT,
        /** 評価式 */
        EXPRESSION,
        /** 開始タグ */
        TAG_START,
        /** 終了タグ */
        TAG_END,
        /** タグ本体 */
        TAG_BODY,
        /** 空要素 */
        TAG_EMPTY_ELEMENTS,
        /** 定義の開始 */
        DEF_START,
        /** 定義の本体 */
        DEF_CDATA,
        /** 定義の終了 */
        DEF_END,
        /** エスケープ文字 */
        ESCAPE,
        /** 関数 */
        FUNCTIONS,
    }

    /**
     * 属性情報のインスタンスを取得する
     * @param key キー項目
     * @param value 値
     * @return 属性情報
     */
    public static final Attribute attr(String key, String value) {
        return new Attribute(key, value);
    }

    /**
     * Fragmentの属性情報（key=value）.
     * <p>
     * keyとvalueはnullを許可しない.<br />
     * このクラスは不変オブジェクトを構成する.
     * </p>
     * @author shuji
     * @since 0.1
     */
    public static final class Attribute {
        /** キー項目 */
        public final String key;
        /** 値 */
        public final String value;

        /**
         * コンストラクタ.
         * @param key キー項目
         * @param value 値
         * @throws IllegalArgumentException キー項目または値がnullの場合
         */
        public Attribute(String key, String value) {
            if (key == null) throw new IllegalArgumentException("key is null.");
            if (value == null) throw new IllegalArgumentException("value is null.");
            this.key = key;
            this.value = value;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Attribute)) return false;
            Attribute attr = (Attribute) obj;
            if (!attr.key.equals(this.key)) return false;
            return attr.value.equals(this.value);
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            int hash = 17;
            hash = hash * 7 + this.key.hashCode();
            hash = hash * 7 + this.value.hashCode();
            return hash;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

}
