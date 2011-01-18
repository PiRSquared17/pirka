package org.pirkaengine.slim3;

import java.nio.charset.Charset;

import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.slim3.internal.DatastorePirkaLoaderFactory;
import org.pirkaengine.slim3.internal.MemcachePirkaLoaderFactory;

/**
 * PirkaenngineをSlim3上で利用する為に必要な初期化処理を提供する。
 * <p>
 * GAEではファイルシステムが使用出来ないため、 キャッシュ管理はMemcacheまたはDatastoreで行う。<br/>
 * デフォルトはMemcahceだがシステムプロパティ<code>org.pirkaengine.cache.mode</code>にdatastoreを設定する事で
 * Datastoreを利用することができる。
 * </p>
 * <p>
 * アプリケーションの初期化時に、initメソッドで初期化処理を実行する。<br>
 * <code>PirkaengineOnSlim3.init();</code>
 * </p>
 * <p>
 * システムプロパティは、appengine-web.xmlで以下のように記述する。
 * <pre><code>
 *  &lt;system-properties&gt;
 *   &lt;property name="org.pirkaengine.cache.mode" value="datastore" /&gt;
 *  &lt;system-properties&gt;
 * </code></pre></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public abstract class PirkaengineOnSlim3 extends PirkaLoader {

    /** Key of cache mode. */
    public static final String KEY_CACHE_MODE = "org.pirkaengine.cache";
    /** Memcache mode (Default) */
    public static final String KEY_CACHE_MODE_MEMCACHE = "memcache";
    /** Datastore mode */
    public static final String KEY_CACHE_MODE_DATASTORE = "datastore";
    /** Key of template file charset */
    public static final String KEY_CHARSET = "org.pirkaengine.charset";
    /** Default charset */
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * PirkaEngineのデフォルト設定を行う
     */
    public static void init() {
        PirkaContext ctx = PirkaContext.getInstance();
        ctx.setTemplatePath("");
        ctx.setEnableCache(true);
        ctx.setEnableScriptThread(false);
        String mode = System.getProperty(KEY_CACHE_MODE);
        if (mode == null || !mode.equalsIgnoreCase(KEY_CACHE_MODE_DATASTORE)) {
            PirkaLoader.setFactory(new MemcachePirkaLoaderFactory());
        } else {
            PirkaLoader.setFactory(new DatastorePirkaLoaderFactory());
        }
        String charset = System.getProperty(KEY_CHARSET, DEFAULT_CHARSET);
        ctx.setCharset(Charset.forName(charset));
    }
}
