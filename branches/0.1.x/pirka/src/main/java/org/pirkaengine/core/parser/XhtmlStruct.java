package org.pirkaengine.core.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * XHTML 構造クラス.
 * <p>
 * XHTMLをパースし、意味のある断片（Fragment）に分割された構造をとる。
 * また、Fragmentは何文字目から何文字目までが、どんな構造を持つかの情報を持ち、
 * XHTMLの文字列全体をtextとして保持する。
 * </p>
 * <p>
 * Fragmentのリストは、テンプレートの構築時に後ろから処理する方が都合が良いため、逆順に保持される。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @sett Fragment
 */
public class XhtmlStruct {

    private final LinkedList<Fragment> fragments;
    private final StringBuilder text;
    private final String baseTemplate;

    /**
     * コンストラクタ.
     * @param text
     * @param fragments
     * @param baseTemplate
     */
    public XhtmlStruct(StringBuilder text, LinkedList<Fragment> fragments, String baseTemplate) {
        this.text = text;
        this.fragments = fragments;
        this.baseTemplate = baseTemplate;
    }

    /**
     * テキストを取得する.
     * <p>
     * バッファはコピーではないので操作する場合は注意
     * </p>
     * @return テキストバッファ
     */
    public StringBuilder getText() {
        return this.text;
    }

    /**
     * Fragmentの一覧を取得する.
     * @return Fragment
     */
    public List<Fragment> getFragments() {
        return this.fragments;
    }
    
    /**
     * 最初に積まれているFragmentを返す
     * @return テンプレートの構造としては最後の断片
     */
    public Fragment getFirst() {
        return this.fragments.getFirst();
    }

    /**
     * 最後に積まれているFragmentを返す
     * @return テンプレートの構造としては最初の断片
     */
    public Fragment getLast() {
        return this.fragments.getLast();
    }
    
    /**
     * ベーステンプレートを取得する
     * @return ベーステンプレート名、ない場合はnull
     */
    public String getBaseTemplate() {
        return this.baseTemplate;
    }
}
