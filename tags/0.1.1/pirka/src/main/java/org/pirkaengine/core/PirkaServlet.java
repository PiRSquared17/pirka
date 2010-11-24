package org.pirkaengine.core;

import static org.pirkaengine.core.util.Logger.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pirkaengine.core.parser.ParseException;


/**
 * Pirka Servlet.
 * <p>
 * PirkaをJ2EE WebアプリケーションのViewとして使用する場合に使用するServlet.
 * 各種フレームワークやServletでは、PirkaServletにforwardする事でPirkaをテンプレートエンジンとして使用する。
 * 
 * </p>
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		debug("PirkaServlet init start.");
		// init context
		ServletConfig config = getServletConfig();
		PirkaContext context = PirkaContext.getInstance();
		String contextPath = config.getInitParameter("contextPath");
		if (contextPath != null) {
			context.setAppRoot(contextPath);
		}
		context.setTemplatePath(getServletContext().getRealPath("/"));
		String enableCache = config.getInitParameter("enableCache");
		if (enableCache != null && enableCache.equalsIgnoreCase("true")) {
			context.setEnableCache(true);
		} else {
			context.setEnableCache(false);
		}
		String debug = config.getInitParameter("enableDebug");
		if (debug != null && debug.equalsIgnoreCase("true")) {
			context.setEnableDebug(true);
		} else {
			context.setEnableDebug(false);
		}
		debug(context);
		debug("PirkaServlet init end.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doService(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doService(req, resp);
	}

	/**
	 * リクエストの処理を行う. リクエストのパスをテンプレートパスとして、テンプレートを読み込む。
	 * requestにviewModelで設定されたモデルをレンダリングに使用する
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PirkaLoader loader = PirkaLoader.newInstance();
		String templateFileName = request.getPathInfo();
		try {
			Template template = loader.load(templateFileName);
			// TODO 文字コード
			response.setCharacterEncoding("UTF-8");
			Object obj = request.getAttribute("viewModel");
			if (obj instanceof Map) {
				response.getWriter().write(
						template.generate((Map<String, Object>) obj).render());
			} else {
				response.getWriter().write(template.generate().render());
			}
		} catch (PirkaLoadException e) {
			// TODO 例外発生時のハンドリング
			error("Load Error.", e);
			throw new ServletException(e);
		} catch (ParseException e) {
			// TODO 例外発生時のハンドリング
			error("Parse Error.", e);
			throw new ServletException(e);
		} catch (TemplateNotFoundException e) {
			// TODO 例外発生時のハンドリング
			error("TemplateNotFound.", e);
			throw new ServletException(e);
		}
	}

}
