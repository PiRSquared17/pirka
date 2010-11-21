package org.pirkaengine.core.template;

import java.io.Serializable;
import java.util.Map;

import org.pirkaengine.core.expression.Function;


/**
 * 表示条件を返すインターフェイス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface Condition extends Serializable {
    
    /**
     * 評価式を評価し表示対象である場合にtrueを返す.
     * @param expression 評価式
     * @param model モデル
     * @param functions 関数
     * @return 表示対象である場合、true
     */
    boolean isDisplay(String expression, Map<String, Object> model, Map<String, Function> functions);
}
