package org.pirkaengine.core.script;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.JavaFunction;


/**
 * 実行クラスファクトリ.
 * <p>
 * マルチスレッド対応の場合、
 * スクリプトエンジンは、それぞれの実行エンジンを持ち、
 * コンフリクトを防止するためにスクリプト毎にクラスローダを作成する。
 * このファクトリクラスではコンテキストからクラスローダを作成し、
 * 実行クラスのインスタンスに設定する責務を持つ。
 * </p>
 * <p>
 * シングルスレッドモードの場合、
 * スクリプトエンジンは実行エンジンを持つが、クラスローダはデフォルトのクラスローダを使用する
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class InvokerFactory {

    private static final InvokerFactory INSTANCE = new InvokerFactory();

    /**
     * シングルトンのファクトリオブジェクトを取得する.
     * @return ファクトリオブジェクト
     */
    public static final InvokerFactory getInstance() {
        return INSTANCE;
    }

    private final Map<String, Invoker> invokers;
    private final PirkaContext context;

    /**
     * コンストラクタ.
     */
    InvokerFactory() {
        this(PirkaContext.getInstance());
    }

    /**
     * コンストラクタ.
     * @param context コンテキスト
     */
    InvokerFactory(PirkaContext context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        this.context = context;
        this.invokers = Collections.synchronizedMap(new HashMap<String, Invoker>());
    }

    /**
     * Invokerを取得する.
     * <p>
     * 指定した関数からInvokerを生成する。
     * １度生成したInvokerは再利用される。
     * </p>
     * @param function 関数
     * @return Invoker
     * @throws ScriptEngineException 
     */
    public synchronized Invoker getInvoker(Function function) throws ScriptEngineException {
        if (function == null) throw new IllegalArgumentException("function == null");
        if (function instanceof JavaFunction) {
            return Invoker.JAVA_STATIC_METHOD_INVOKER;
        }
        String language = function.language;
        Invoker invoker = this.invokers.get(language);
        if (invoker == null) {
            if (context.isEnableScriptThread()) {
                ClassLoader classLoader = getClassLoader(language);
                invoker = new MultiThreadScriptInvoker(language, classLoader);
            } else {
                invoker = new SingleThreadScriptInvoker(language);
            }
            this.invokers.put(language, invoker);
        }
        return invoker;
    }

    private ClassLoader getClassLoader(String language) throws ScriptEngineException {
        String path = context.getPathToScript(language);
        if (path == null) return Thread.currentThread().getContextClassLoader();
        try {
            File libPath = new File(path);
            File[] jars = libPath.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });
            URL[] urls = new URL[jars.length];
            for (int i = 0; i < urls.length; i++) {
                urls[i] = jars[i].toURI().toURL();
            }
            return new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            throw new ScriptEngineException("wrong path: " + path, e);
        }
    }
}
