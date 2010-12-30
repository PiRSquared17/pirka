package org.pirkaengine.slim3;

import java.io.IOException;
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

    public static Navigation render(HttpServletRequest req, HttpServletResponse resp, String templateName)
            throws IOException, RenderException, ParseException, PirkaLoadException, TemplateNotFoundException {
        return render(req, resp, templateName, new HashMap<String, Object>());
    }

    public static Navigation render(HttpServletRequest req, HttpServletResponse resp, String templateName,
            Map<String, Object> viewModel) throws IOException, RenderException, ParseException, PirkaLoadException,
            TemplateNotFoundException {
        if (resp == null) throw new IllegalArgumentException("resp == null");
        if (templateName == null) throw new IllegalArgumentException("templateName == null");
        if (viewModel == null) throw new IllegalArgumentException("viewModel == null");
        long start = System.currentTimeMillis();
        try {
            Template template = PirkaLoader.newInstance().load(templateName);
            resp.setCharacterEncoding(req.getCharacterEncoding());
            String contextType = "text/html; charset=" + resp.getCharacterEncoding();
            resp.setContentType(contextType);
            viewModel.put("contextType", contextType);
            template.generate(viewModel).render(resp.getOutputStream(), resp.getCharacterEncoding());
            resp.flushBuffer();
            return null;
        } finally {
            if (logger.isLoggable(Level.FINE)) logger.fine("PirkaEngine rendering: "
                    + (System.currentTimeMillis() - start) + "[msec]");
        }
    }
}
