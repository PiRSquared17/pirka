package org.pirkaengine.slim3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.pirkaengine.core.PirkaException;
import org.slim3.controller.Navigation;

import scenic3.ScenicPage;

/**
 * Slim3+Scenic3でPirkaEngineを利用する場合に利用できるコントローラ基底クラス.
 * <p>
 * Example)<pre></code>
 * &#064;Page("/")
 * public class TweetPage extends PirkaPage {
 *   &#064;Default
 *   public Navigation list() throws Exception {
 *     List&lt;Tweet&gt; tweetList = new TweetService().getList();
 *     viewModel("list", tweetList);
 *     return render("list.html");
 *   }
 * }
 * </code></pre>
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class PirkaPage extends ScenicPage {
    /** ViewModel */
    protected Map<String, Object> viewModel = new HashMap<String, Object>();

    /**
     * キーを指定して、ViewModelにオブジェクトを設定する
     * @param key キー
     * @param value オブジェクト
     * @throws IllegalArgumentException keyがnullの場合
     */
    protected void viewModel(String key, Object value) {
        if (key == null) throw new IllegalArgumentException("key is null.");
        viewModel.put(key, value);
    }

    /**
     * キーを指定して、viewModelからオブジェクトを取得する
     * @param <T> オブジェクトの型
     * @param key キー
     * @return オブジェクト
     * @throws IllegalArgumentException keyがnullの場合
     */
    @SuppressWarnings("unchecked")
    protected <T> T viewModel(String key) {
        if (key == null) throw new IllegalArgumentException("key is null.");
        return (T) viewModel.get(key);
    }

    /**
     * テンプレートを指定してレンダリングを行う
     * @param templateName テンプレートファイル名
     * @return Navigation
     * @throws PirkaException テンプレートのロード・パース・レンダリング等に失敗した場合
     * @throws IOException 入出力例外発生時
     * @throws IllegalArgumentException templateNameがnullの場合
     */
    protected Navigation render(String templateName) throws PirkaException, IOException {
        if (templateName == null) throw new IllegalArgumentException("templateName is null.");
        return PirkaRenderer.render(request, response, templateName, viewModel);
    }

}
