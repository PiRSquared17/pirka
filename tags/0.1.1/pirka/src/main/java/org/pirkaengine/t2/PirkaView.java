package org.pirkaengine.t2;

import static org.pirkaengine.core.util.Logger.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import org.pirkaengine.core.ModelDeficientPropertyException;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.Renderer;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;
import org.pirkaengine.core.util.EqualsHelper;
import org.pirkaengine.core.util.ViewModel;
import org.t2framework.t2.contexts.Response;
import org.t2framework.t2.contexts.WebContext;
import org.t2framework.t2.spi.Navigation;

/**
 * Pirka用T2 Navigationクラス.
 * <p>
 * T2でPlikaを使用する場合は、このクラスのstaticメソッドを静的インポートし、renderToメソッドを使用します。<br />
 * レンダリングするモデルには、StringとObjectのMapを用いるか、１つのオブジェクトを指定します。<br />
 * １つのオブジェクトを指定した場合、publicフィールドやgetterを自然な形にマッピングして、レンダリングします。
 * </p>
 * <pre>
 * <code>
 * import static org.pirkaengine.core.t2.PirkaView.renderTo;
 * 
 * public class SamplePage { 
 *    public Navigation index() {
 *       Map<String, Object> viewModel = new HashMap<String, Object>();
 *       viewModel.put("param", "T2 Sample");
 *       return renderTo("index.html", viewModel);
 *     }
 *   }
 *   public Navigation action() {
 *     return renderTo("action.html", this);
 *   }
 * }
 * </code>
 * </pre>
 * @author shuji
 * @since 1.0
 */
public class PirkaView implements Navigation {

    /** テンプレート名 */
    private final String templateName;
    /** モデル */
    private final Object model;
    /** ビューモデル */
    private final Map<String, Object> viewModel;

    /**
     * テンプレートを指定し、レンダリングを行うPirkaViewを取得する.
     * @param templateName テンプレート名
     * @return PirkaView
     */
    public static PirkaView renderTo(String templateName) {
        if (templateName == null) throw new IllegalArgumentException("templateName is null.");
        return new PirkaView(templateName);
    }

    /**
     * テンプレートとビューモデルを指定し、レンダリングを行うPirkaViewを取得する.
     * @param templateName テンプレート名
     * @param viewModel ビューモデル
     * @return PirkaView
     */
    public static PirkaView renderTo(String templateName, Map<String, Object> viewModel) {
        if (templateName == null) throw new IllegalArgumentException("templateName is null.");
        if (viewModel == null) throw new IllegalArgumentException("viewModel is null.");
        return new PirkaView(templateName, viewModel);
    }

    /**
     * テンプレートとモデルを指定し、レンダリングを行うPirkaViewを取得する.
     * @param templateName テンプレート名
     * @param model モデル
     * @return PirkaView
     */
    public static PirkaView renderTo(String templateName, Object model) {
        if (templateName == null) throw new IllegalArgumentException("templateName is null.");
        if (model == null) throw new IllegalArgumentException("model is null.");
        return new PirkaView(templateName, model);
    }

    /**
     * コンストラクタ.
     * @param templateName テンプレート名
     */
    public PirkaView(String templateName) {
        this.templateName = templateName;
        this.model = null;
        this.viewModel = null;
    }

    /**
     * コンストラクタ.
     * @param templateName テンプレート名
     * @param model モデル
     */
    public PirkaView(String templateName, Object model) {
        this.templateName = templateName;
        this.model = model;
        this.viewModel = null;
    }

    /**
     * コンストラクタ.
     * @param templateName テンプレート名
     * @param viewModel ビューモデル
     */
    public PirkaView(String templateName, Map<String, Object> viewModel) {
        this.templateName = templateName;
        this.model = null;
        this.viewModel = viewModel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.t2framework.t2.spi.Navigation#execute(org.t2framework.t2.contexts
     * .WebContext)
     */
    @Override
    public void execute(WebContext context) throws Exception {
        Response response = context.getResponse();
        // TODO charset
        response.setContentType("text/html; charset=utf-8");
        long start = System.currentTimeMillis();
        getRenderer().render(context.getResponse().getNativeResource().getOutputStream());
        if (isTraceEnabled()) trace("execute finished: " + (System.currentTimeMillis() - start) + "[msec]");
    }

    private Renderer getRenderer() throws ParseException, PirkaLoadException, TemplateNotFoundException,
            ModelDeficientPropertyException {
        Template template = PirkaLoader.newInstance().load(templateName);
        if (this.viewModel != null) {
            return template.generate(viewModel);
        }
        if (this.model == null) {
            return template.generate();
        }
        return template.generate(ViewModel.create(template, model));
    }
    
    public void render(OutputStream out) throws IOException {
        try {
            getRenderer().render(out);
        } catch (RenderException e) {
            throw new IOException(e);
        } catch (ParseException e) {
            throw new IOException(e);
        } catch (PirkaLoadException e) {
            throw new IOException(e);
        } catch (TemplateNotFoundException e) {
            throw new IOException(e);
        } catch (ModelDeficientPropertyException e) {
            throw new IOException(e);
        }
    }

    public void render(Writer writer)  throws IOException {
        try {
            getRenderer().render(writer);
        } catch (RenderException e) {
            throw new IOException(e);
        } catch (ParseException e) {
            throw new IOException(e);
        } catch (PirkaLoadException e) {
            throw new IOException(e);
        } catch (TemplateNotFoundException e) {
            throw new IOException(e);
        } catch (ModelDeficientPropertyException e) {
            throw new IOException(e);
        }
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PirkaView)) return false;
        PirkaView that = (PirkaView) obj;
        if (!EqualsHelper.equals(this.templateName, that.templateName)) return false;
        if (!EqualsHelper.equals(this.model, that.model)) return false;
        if (!EqualsHelper.equals(this.viewModel, that.viewModel)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 13;
        hash = hash * 17 + (this.templateName != null ? templateName.hashCode() : 0);
        hash = hash * 17 + (this.model != null ? model.hashCode() : 0);
        hash = hash * 17 + (this.viewModel != null ? viewModel.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("PirkaView[");
        str.append("templateName=").append(this.templateName);
        str.append(", model=").append(this.model);
        str.append(", viewModel=").append(this.viewModel);
        str.append("]");
        return str.toString();
    }
}
