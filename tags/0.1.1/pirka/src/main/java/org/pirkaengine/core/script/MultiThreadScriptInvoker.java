package org.pirkaengine.core.script;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.pirkaengine.core.expression.Function;


/**
 * ScriptInvoker.
 * <p>
 * スクリプトの実行クラスを提供する。<br />
 * ScriptInvokerでは、コンストラクタで指定されたClassLoaderを用いてスクリプトを実行する。
 * ScriptInvokerでは実行用スレッドを作成し、そのスレッド内で使用されるClassLoaderを保証する。
 * </p>
 * <p>
 * このクラスはスレッドセーフを保証する。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class MultiThreadScriptInvoker extends ScriptInvoker {

    private final ExecutorService service;
    private final ScriptEngine engine;

    @Override
    ScriptEngine getEngine() {
        return engine;
    }

    /**
     * スクリプト言語とClassLoaderを指定してインスタンスを生成する。
     * <p>
     * 実行用のスレッドはExecutorServiceとして初期化される。
     * それらのスレッドのClassLoaderは引数で指定されたClassLoaderが設定される。
     * </p>
     * <p>
     * また、実行するScriptEngineは指定されたClassLoaderから取得される。
     * </p>
     * @param language スクリプト言語
     * @param classLoader ClassLoader
     * @throws ScriptEngineException
     */
    public MultiThreadScriptInvoker(final String language, final ClassLoader classLoader) throws ScriptEngineException {
        if (language == null) throw new IllegalArgumentException("language == null");
        if (classLoader == null) throw new IllegalArgumentException("classLoader == null");
        this.service = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setContextClassLoader(classLoader);
                return thread;
            }
        });
        Future<ScriptEngine> future = this.service.submit(new Callable<ScriptEngine>() {
            @Override
            public ScriptEngine call() {
                ScriptEngineManager mng = new ScriptEngineManager();
                return mng.getEngineByName(language);
            }
        });
        try {
            engine = future.get();
        } catch (InterruptedException e) {
            throw new ScriptEngineException("init error.", e);
        } catch (ExecutionException e) {
            throw new ScriptEngineException("init error.", e);
        }
        if (engine == null) throw new ScriptEngineException("Cant find ScriptEngineManager: language=" + language);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.pirkaengine.core.script.Invoker#invoke(org.pirkaengine.core.expression
     * .Function, java.lang.Object[])
     */
    @Override
    public Object invoke(final Function function, final Object[] params) throws ScriptEngineException {
        Future<Object> future = service.submit(new Callable<Object>() {
            @Override
            public Object call() throws ScriptException, NoSuchMethodException {
                return getInvocable(function).invokeFunction(function.name, params);
            }
        });
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new ScriptEngineException("function invoke error.", e);
        } catch (ExecutionException e) {
            throw new ScriptEngineException("function invoke error.", e);
        }
    }

}
