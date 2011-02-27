package org.pirkaengine.slim3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;
import org.slim3.controller.Navigation;

/**
 * PirkaEngineでレンダリングを行う為のユーティリティクラス.
 * <p>
 * Slim3を利用したアプリケーションでPirkaEngineを利用する場合には次のようにして
 * ViewModelを作成し、レンダリングメソッドを実行してください。<br>
 * <pre><code>
 * public Navigation run() throws Exception {
 *   Map<String, Object> viewModel = new HashMap<String, Object>();
 *   viewModel.put("name", name);
 *   viewModel.put("items", itemList);
 *   return PirkaRenderer.render(request, response, "template_file.html", viewModel);
 * }
 * </code></pre>
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaRenderer {

    /** The logger. */
    private static final Logger logger = Logger.getLogger(PirkaRenderer.class.getName());

    /**
     * 指定したテンプレートでレンダリングを行う
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param templateName テンプレート
     * @return null
     * @throws IOException
     * @throws RenderException
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    public static Navigation render(HttpServletRequest req, HttpServletResponse resp, String templateName)
            throws IOException, RenderException, ParseException, PirkaLoadException, TemplateNotFoundException {
        return render(req, resp, templateName, new HashMap<String, Object>());
    }

    /**
     * ViewModelとテンプレートを指定して、レンダリングを行う
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param templateName テンプレート
     * @param viewModel ビューモデル
     * @return null
     * @throws IOException
     * @throws RenderException
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    public static Navigation render(HttpServletRequest req, HttpServletResponse resp, String templateName,
            Map<String, Object> viewModel) throws IOException, RenderException, ParseException, PirkaLoadException,
            TemplateNotFoundException {
        String encoding = req.getCharacterEncoding();
        setUpResponse(resp, viewModel, "text/html", encoding);
        return render(req, resp, templateName, viewModel, Charset.forName(encoding));
    }

    public static Navigation render(HttpServletRequest req, HttpServletResponse resp, String templateName,
            Map<String, Object> viewModel, Charset charset) throws IOException, RenderException, ParseException,
            PirkaLoadException, TemplateNotFoundException {
        if (resp == null) throw new IllegalArgumentException("resp == null");
        if (templateName == null) throw new IllegalArgumentException("templateName == null");
        if (viewModel == null) throw new IllegalArgumentException("viewModel == null");
        if (charset == null) throw new IllegalArgumentException("charset == null");
        long start = System.currentTimeMillis();
        try {
            Template template = PirkaLoader.newInstance().load(templateName);
            template.generate(viewModel).render(resp.getOutputStream(), charset);
            resp.flushBuffer();
            return null;
        } finally {
            if (logger.isLoggable(Level.FINE)) logger.fine("PirkaEngine rendering: "
                    + (System.currentTimeMillis() - start) + "[msec]");
        }
    }

    static void setUpResponse(HttpServletResponse resp, Map<String, Object> viewModel, String content, String encoding) {
        String contentType = content + "; charset=" + encoding;
        viewModel.put("contentType", contentType);
        resp.setCharacterEncoding(encoding);
        resp.setContentType(contentType);
    }

}
