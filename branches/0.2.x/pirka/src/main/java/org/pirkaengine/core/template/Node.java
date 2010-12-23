package org.pirkaengine.core.template;

import java.io.Serializable;
import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * テンプレートのノード．
 * <p>
 * テンプレートはNodeのツリー構造となる。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * テキストを取得する.
     * @param model モデル
     * @param functions 関数セット
     * @return レンダリング結果
     */
    public abstract String getText(Map<String, Object> model, Map<String, Function> functions);
    
    /**
     * 評価式の一覧を取得する.
     * <p>
     * foo
     * hoge.huga
     * foo.poo()
     * といった書式で返す。
     * 
     * 存在しない場合は、サイズゼロの配列を返す。
     * </p>
     * @return 評価式の一覧
     */
    public String[] getExpressions() {
        return new String[0];
    }

}
