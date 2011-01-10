package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.Logger.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import org.pirkaengine.core.PirkaRuntimeException;
import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.Renderer;
import org.pirkaengine.core.expression.Function;


/**
 * テンプレートにモデルを紐づけたレンダラークラス.
 * <p>
 * このクラスではTemplateとModel(viewModel)を1:1に紐付ける。<br />
 * また、Rendererを実装し、テンプレートのレンダリング機能を実装する。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class BindTemplate implements Renderer {
    /** Template */
    private final XhtmlTemplate template;
    /** Model */
    private final Map<String, Object> viewModel;
    /** Script Functions */
    private final Map<String, Function> scriptFunctions;

    /**
     * コンストラクタ.
     * @param template
     * @param viewModel
     * @param scriptFunctions
     */
    public BindTemplate(XhtmlTemplate template, Map<String, Object> viewModel, Map<String, Function> scriptFunctions) {
        this.template = template;
        this.viewModel = viewModel;
        this.scriptFunctions = scriptFunctions;
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.Renderer#render()
     */
    @Override
    public String render() throws RenderException {
        try {
            StringBuilder text = new StringBuilder();
            for (Node node : this.template.nodeList) {
                text.append(node.getText(viewModel, scriptFunctions));
            }
            return text.toString();
        } catch (PirkaRuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw e;
        } catch (RuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw new RenderException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.Renderer#render(java.io.OutputStream)
     */
    @Override
    public void render(OutputStream output) throws RenderException {
        render(output, "UTF-8");
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.Renderer#render(java.io.OutputStream, java.lang.String)
     */
    @Override
    public void render(OutputStream output, String charset) throws RenderException {
        if (charset == null) throw new IllegalArgumentException("charset is null.");
        render(output, Charset.forName(charset));
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.Renderer#render(java.io.OutputStream, java.nio.charset.Charset)
     */
    @Override
    public void render(OutputStream output, Charset charset) throws RenderException {
        if (output == null) throw new IllegalArgumentException("output is null.");
        if (charset == null) throw new IllegalArgumentException("charset is null.");
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(output, charset);
            writeTemplate(writer);
        } catch (IOException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw new RenderException(e);
        } catch (PirkaRuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw e;
        } catch (RuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw new RenderException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.Renderer#render(java.io.Writer)
     */
    @Override
    public void render(Writer writer) throws RenderException {
        if (writer == null) throw new IllegalArgumentException("writer is null.");
        try {
            writeTemplate(writer);
        } catch (IOException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw new RenderException(e);
        } catch (PirkaRuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw e;
        } catch (RuntimeException e) {
            error("Rendering Error: " + this.template.templateName, e);
            throw new RenderException(e);
        }
    }

    private void writeTemplate(Writer writer) throws IOException {
        assert writer != null;
        for (Node node : this.template.nodeList) {
            writer.write(node.getText(viewModel, scriptFunctions));
        }
        writer.flush();
    }

}
