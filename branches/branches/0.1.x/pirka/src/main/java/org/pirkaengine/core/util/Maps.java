package org.pirkaengine.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Viewに渡すMapを生成するユーティリティクラス.
 * <code>
 * Map<String, Object> viewModel = Maps.viewModel()
 *                                     .width("key1", value1)
 *                                     .with("key2", value2)
 *                                     .map();
 * </code>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class Maps {
    
    /**
     * viewModelのビルダを作成する
     * @return Maps
     */
    public static Maps viewModel() {
        return new Maps();
    }
    
    /**
     * １つのキー/バリューを持つMapを生成する
     * @param key
     * @param value
     * @return Map
     */
    public static Map<String, Object> viewModel(String key, Object value) {
        return Maps.viewModel().with(key, value).map();
    }

    private final HashMap<String, Object> viewModel = new HashMap<String, Object>();

    private Maps() {
    }
    
    /**
     * key/valueを設定する
     * @param key
     * @param value
     * @return this
     */
    public Maps with(String key, Object value) {
        viewModel.put(key, value);
        return this;
    }
    
    /**
     * 構築されたMapを取得する
     * @return Map
     */
    public Map<String, Object> map() {
        return viewModel;
    }
}
