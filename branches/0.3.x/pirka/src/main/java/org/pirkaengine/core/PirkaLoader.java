package org.pirkaengine.core;

import static org.pirkaengine.core.util.Logger.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import org.pirkaengine.core.parser.ParseException;
import org.pirkaengine.core.serialize.CacheManager;
import org.pirkaengine.core.serialize.SerializeException;
import org.pirkaengine.core.template.TemplateBuilder;


/**
 * テンプレートローダー.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaLoader {

    /** PirkaContext */
    final PirkaContext context;
    /** CacheManager */
    final CacheManager cacheManager;

    private static volatile Factory FACTORY = new Factory() {
        /*
         * (non-Javadoc)
         * @see org.pirkaengine.core.PirkaLoader.Factory#newInstance()
         */
        @Override
        public PirkaLoader newInstance() {
            return new PirkaLoader();
        }
    };
    
    /**
     * ファクトリクラスを設定する.
     * @param newFactory
     */
    public static void setFactory(Factory newFactory) {
        if (newFactory == null) throw new IllegalArgumentException("newFactory == null");
        PirkaLoader.FACTORY = newFactory;
    }
    
    /**
     * インスタンスを生成する.
     * @return PirkaLoader
     */
    public static PirkaLoader newInstance() {
        return FACTORY.newInstance();
    }

    /**
     * コンストラクタ.
     */
    public PirkaLoader() {
        this(PirkaContext.getInstance(), CacheManager.getInstance());
    }

    /**
     * コンストラクタ.
     * @param context PirkaContext
     */
    protected PirkaLoader(PirkaContext context) {
        this(context, CacheManager.getInstance());
    }

    /**
     * コンストラクタ.
     * @param cacheManager CacheManager
     */
    protected PirkaLoader(CacheManager cacheManager) {
        this(PirkaContext.getInstance(), cacheManager);
    }

    /**
     * コンストラクタ.
     * @param context PirkaContext
     * @param cacheManager CacheManager
     */
    protected PirkaLoader(PirkaContext context, CacheManager cacheManager) {
        if (context == null) throw new IllegalArgumentException("context == null");
        if (cacheManager == null) throw new IllegalArgumentException("cacheManager == null");
        this.context = context;
        this.cacheManager = cacheManager;
    }

    /**
     * テンプレートをロードする.
     * <p>
     * コンテキストでキャッシュが有効になっている場合、システム内に保持されているキャッシュを取得する。
     * キャッシュが存在し、保持されているタイムスタンプがテンプレートと一致する場合、キャッシュされたインスタンスを返す。
     * そうでない場合、テンプレートはパースされる。また、キャッシュとして保存される。
     * </p>
     * <p>
     * コンテキストでキャッシュが無効になっている場合、キャッシュを使用せずに毎回パース処理を行う。
     * </p>
     * 
     * @param templateFileName テンプレートファイル名
     * @return テンプレート
     * @throws ParseException パース処理に失敗した場合
     * @throws TemplateNotFoundException Templateファイルが存在しない場合
     * @throws PirkaLoadException キャッシュ処理に失敗した場合 TODO どうする？
     */
    public Template load(String templateFileName) throws ParseException, PirkaLoadException, TemplateNotFoundException {
        return load(templateFileName, PirkaContext.getInstance().getCharset());
    }
    
    public Template load(String templateFileName, Charset charset) throws ParseException, PirkaLoadException, TemplateNotFoundException {
        if (templateFileName == null) throw new IllegalArgumentException("templateFileName is null.");
        if (isDebugEnabled()) debug("load: " + templateFileName);
        File file = toFile(templateFileName);
        try {
            if (!this.context.isEnableCache()) return build(templateFileName, file, charset);
            Template template = cacheManager.deserialize(templateFileName);
            if (template != null && template.getTimeStamp() == file.lastModified()) return template;
            template = build(templateFileName, file, charset);
            cacheManager.serialize(template);
            return template;
        } catch (ParseException e) {
            error("Parse Error: " + templateFileName, e);
            throw e;
        } catch (FileNotFoundException e) {
            TemplateNotFoundException ex = new TemplateNotFoundException("templateFileName:" + templateFileName, e);
            error("Template Not Found: " + templateFileName, ex);
            throw ex;
        } catch (SerializeException e) {
            PirkaLoadException ex = new PirkaLoadException("Serialize Error.", e);
            error("Serialize Error: " + templateFileName, ex);
            throw ex;
        } catch (RuntimeException e) {
            error("Runtime Error: " + templateFileName, e);
            throw e;
        }
    }
    
    /**
     * テンプレート名をファイルに変換する.
     * @param templateName テンプレート名
     * @return ファイルオブジェクト
     */
    public File toFile(String templateName) {
        return new File(context.getTemplatePath() + templateName);
    }

    /**
     * テンプレート名をファイルに変換する.
     * @param templateName テンプレート名
     * @param baseTemplateName
     * @return ファイルオブジェクト
     */
    public File toFile(String templateName, String baseTemplateName) {
        int idx = baseTemplateName.lastIndexOf('/');
        if (idx == -1) return toFile(templateName);
        return new File(context.getTemplatePath() + baseTemplateName.substring(0, idx + 1) + templateName);
    }
    
    private Template build(String templateFileName, File file, Charset charset) throws ParseException, PirkaLoadException, TemplateNotFoundException,
            FileNotFoundException {
        if (isDebugEnabled()) debug("build: " + file.getAbsolutePath());
        if (!file.exists()) throw new TemplateNotFoundException("Not Found template: " + file.getAbsolutePath());
        TemplateBuilder builder = new TemplateBuilder(this);
        Template template = builder.build(templateFileName, file, charset);
        return template;
    }

    /**
     * ファクトリインターフェイス.
     * @author shuji.w6e
     * @since 0.1.0
     */
    public static interface Factory {
        /**
         * インスタンスを生成する.
         * @return PirkaLoader
         */
        PirkaLoader newInstance();
    }
}
