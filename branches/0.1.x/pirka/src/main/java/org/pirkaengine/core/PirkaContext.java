package org.pirkaengine.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Pirka Context.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaContext implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final PirkaContext INSTANCE = new PirkaContext();
    /** アプリケーションルートパス */
    private String appRoot = "/";
    /** テンプレートパス */
    private String templatePath = "template/";
    /** キャッシュパス */
    private String cachePath = "tmp/cache/";
    /** キャッシュの有効 */
    private boolean enableCache = true;
    /** スクリプト用スレッドの有効 */
    private boolean enableScriptThread = true;
    /** Scriptエンジンへのパス設定 */
    private Map<String, String> scriptsLibs = new HashMap<String, String>();
    /** デバッグの有効 */
    private boolean enableDebug = false;

    /**
     * シングルトンのインスタンスを取得する.
     * @return Contextインスタンス
     */
    public static PirkaContext getInstance() {
        return INSTANCE;
    }

    /**
     * アプリケーションルートパスを設定する.
     * @param appRoot アプリケーションルートパス
     * @throws IllegalArgumentException 指定したパスがnullの場合
     */
    public void setAppRoot(String appRoot) {
        if (appRoot == null) throw new IllegalArgumentException("appRoot == null.");
        StringBuilder path = new StringBuilder(appRoot);
        if (appRoot.equals("/") || path.length() == 0) {
            this.appRoot = "/";
        } else {
            if (path.charAt(0) != '/') path.insert(0, '/');
            if (path.charAt(path.length() - 1) != '/') path.append('/');
            this.appRoot = path.toString();
        }
    }

    /**
     * テンプレートパスを設定する.
     * @param templatePath テンプレートパス
     * @throws IllegalArgumentException 指定したパスがnullの場合
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = formatPath(templatePath);
    }

    /**
     * キャッシュパスを設定する.
     * @param cachePath キャッシュパス
     * @throws IllegalArgumentException 指定したパスがnullの場合
     */
    public void setCachePath(String cachePath) {
        this.cachePath = formatPath(cachePath);
    }

    private String formatPath(String path) {
        if (path == null) throw new IllegalArgumentException("path is null");
        if (0 < path.length() && !path.endsWith("/")) return path + "/";
        return path;
    }

    /**
     * キャッシュの有効を設定する.
     * @param enableCache キャッシュを有効にする場合はtrue
     */
    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }
    
    /**
     * ScriptThreadの有効を設定する.
     * @param enableScriptThread ScriptThreadを有効にする場合はtrue
     */
    public void setEnableScriptThread(boolean enableScriptThread) {
        this.enableScriptThread = enableScriptThread;
    }
    

    /**
     * アプリケーションルートパスを取得.
     * @return アプリケーションルートパス
     */
    public String getAppRoot() {
        return appRoot;
    }

    /**
     * テンプレートパスを取得.
     * @return テンプレートパス
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * キャッシュパスを取得.
     * @return キャッシュパス
     */
    public String getCachePath() {
        return cachePath;
    }

    /**
     * キャッシュが有効な場合にtrueを返す
     * @return  キャッシュが有効な場合にtrue
     */
    public boolean isEnableCache() {
        return enableCache;
    }

    /**
     * ScriptThreadが有効な場合にtrueを返す
     * @return  ScriptThreadが有効な場合にtrue
     */
    public boolean isEnableScriptThread() {
        return enableScriptThread;
    }
    
    /**
     * スクリプトエンジンへのパスを設定する.
     * @param language
     * @param pathToLib
     */
    public void addScriptSupport(String language, String pathToLib) {
        scriptsLibs.put(language, pathToLib);
    }

    /**
     * スクリプトエンジンへのパスを設定する.
     * @param language
     * @return パス
     */
    public String getPathToScript(String language) {
        return scriptsLibs.get(language);
    }

    /**
     * デバックが有効な場合にtrueを返す
     * @return デバックが有効な場合にtrue
     */
    public boolean isEnableDebug() {
        return enableDebug;
    }

    /**
     * デバック状態を設定する
     * @param enableDebug デバックを有効にする場合はtrue
     */
    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("PirkaContext[");
        str.append("appRoot=").append(appRoot);
        str.append(", templatePath=").append(templatePath);
        str.append(", cachePath=").append(cachePath);
        str.append(", enableCache=").append(enableCache);
        str.append(", enableScriptThread=").append(enableScriptThread);
        str.append(", enableDebug=").append(enableDebug);
        str.append(", scriptsLibs=").append(scriptsLibs);
        str.append("]");
        return str.toString();
    }

}
