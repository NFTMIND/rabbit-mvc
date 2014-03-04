package os.rabbit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import os.rabbit.components.Component;
import os.rabbit.components.IComponentVisitor;
import os.rabbit.components.WebPage;
import os.rabbit.components.form.FormComponent;

public class RabbitServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(RabbitServlet.class);
	public static final String UNAUTHORIZED_URI = "UNAUTHORIZED_URI";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2493678296014931933L;

	private String encoding = "utf-8";
	private PageFactory pageFactory;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		pageFactory = new PageFactory(getServletContext(), encoding);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private static String resourceDir = "/rbt";

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//resp.setHeader("X-Frame-Options", "DENY");
			
			
			req.setCharacterEncoding(encoding);
			resp.setCharacterEncoding(encoding);

			IKeyValueProvider provider = (IKeyValueProvider) req.getAttribute("KEY_VALUE_PROVIDER");
			if (provider == null) {
				if (ServletFileUpload.isMultipartContent(req)) {
					provider = new FileItemKeyValueProvider(req);
				} else {
					provider = new ReqKeyValueProvider(req);

				}
				req.setAttribute("KEY_VALUE_PROVIDER", provider);
			}
			String locale = (String)provider.get("rbtLocale");
			if(locale != null) {
				req.getSession().setAttribute("locale", locale);
			}

			String contextPath = req.getContextPath();
			String uri = req.getRequestURI();
			if (req.getAttribute("javax.servlet.include.request_uri") != null) {
				uri = (String) req.getAttribute("javax.servlet.include.request_uri");

			}
			req.setAttribute("RBT_REQ_URI", uri);

			if (req.getAttribute("os.rabbit.uri") == null) {
				req.setAttribute("os.rabbit.uri", req.getRequestURI());
			}

			String path = uri.substring(contextPath.length(), uri.length());
			String rbtType = (String) provider.get("rbtType");

			if (path.startsWith(resourceDir)) {

				long ifModifiedSince = req.getDateHeader("If-Modified-Since");

				String resourcePath = "/os/rabbit/resources" + path.substring(4, path.length());

				URL resURL = getClass().getResource(resourcePath);
				if (resURL != null) {
					URLConnection connection = resURL.openConnection();
					if (connection.getLastModified() / 1000 > ifModifiedSince / 1000) {
						resp.setDateHeader("Last-Modified", connection.getLastModified());
						InputStream in = connection.getInputStream();
						OutputStream out = resp.getOutputStream();

						byte[] buffer = new byte[1024];
						while (in.available() > 0) {
							int len = in.read(buffer);
							out.write(buffer, 0, len);
						}
					} else {
						resp.setStatus(304);
					}

				}

				return;

			}
	
			WebPage page = pageFactory.get(path, (String) req.getSession().getAttribute("locale"));

			resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			resp.setHeader("Pragma", "no-cache");

			if (rbtType == null) {
				resp.setContentType("text/html");
				if (page != null) {
					page.requestStart(req, resp);
					//page.setRequestURI(uri);
					try {
						if (!page.isAuthorized()) {
							String unauthorizedURI = getInitParameter("unauthorized");
							if (unauthorizedURI != null) {
								String currentURI = buildCurrentURI(req);
								
								req.setAttribute("UNAUTHORIZED_URI", currentURI);
							
								req.getRequestDispatcher(unauthorizedURI).forward(req, resp);

								return;
							} else {
								throw new AuthenticationException();
							}
						}
						updateValue(provider, page);
						PrintWriter writer = new PrintWriter(page.getWriter());

						page.render(writer);
						if (page.getRedirect() != null) {
							writer.write("<script>location.href=\"" + page.getRedirect() + "\"</script>");
						}
						writer.flush();
						resp.getWriter().write(page.getWriter().toString());
					} catch (RenderInterruptedException e) {
						
						if (page.getRedirect() != null) {
							resp.sendRedirect(page.getRedirect());
						}

					} finally {
						page.requestEnd();
					}
				} else {
					resp.sendError(404);
				}
			} else if (rbtType.equals("INVOKE") || rbtType.equals("AJAX_INVOKE") || rbtType.equals("INVOKE_WITHOUT_PAGE_RENDER")) {

				if (page != null) {
					page.requestStart(req, resp);

					try {
						if (!page.isAuthorized()) {
							String unauthorizedURI = getInitParameter("unauthorized");
							if (unauthorizedURI != null) {
								String currentURI = buildCurrentURI(req);
								
								req.setAttribute("UNAUTHORIZED_URI", currentURI);
								req.getRequestDispatcher(unauthorizedURI).forward(req, resp);

								return;
							} else {
								throw new AuthenticationException();
							}
						}
						updateValue(provider, page);

						StringBuffer ajaxLogger = new StringBuffer();
						final String triggerId = (String) provider.get("triggerId");
						ITrigger trigger = page.getTrigger(triggerId);

						if (trigger != null) {
							trigger.invoke();
						} else {
							logger.info("did not found Trigger '" + triggerId + "'");
							ajaxLogger.append("網頁操作失敗，可能是網頁已經過期，請嘗試重新整理");
						}
						if(page.getRedirect() != null) {
							throw new RenderInterruptedException();
						}
						if (rbtType.equals("INVOKE")) {
							resp.setContentType("text/html");
			
							PrintWriter writer = new PrintWriter(page.getWriter());
							page.render(writer);
							if (page.getRedirect() != null) {
								writer.write("<script>location.href=\"" + page.getRedirect() + "\"</script>");

							}
							writer.flush();
							resp.getWriter().write(page.getWriter().toString());
						} else if (rbtType.equals("AJAX_INVOKE")) {

							resp.setContentType("text/xml");
							PrintWriter writer = resp.getWriter();
							writer.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
							writer.println("<ajax>");
							writer.println(page.getWriter().toString());

							if (ajaxLogger.length() > 0) {
								writer.println("<error><![CDATA[");
								writer.println(ajaxLogger.toString());
								writer.println("]]></error>");
							}
							writer.println("</ajax>");

						} else {
							resp.setContentType("text/html");

							PrintWriter writer = new PrintWriter(page.getWriter());

							writer.flush();
							resp.getWriter().write(page.getWriter().toString());
						}
					} catch (RenderInterruptedException e) {
						if (page.getRedirect() != null) {
							resp.sendRedirect(page.getRedirect());
						}
					} finally {
						page.requestEnd();
					}

				} else {
					resp.sendError(404);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

			throw new ServletException(e);

		}
		resp.flushBuffer();

	}

	private String buildCurrentURI(HttpServletRequest req) {
		String uri = req.getRequestURI();

		if (req.getAttribute("javax.servlet.include.request_uri") != null) {
			uri = (String) req.getAttribute("javax.servlet.include.request_uri");

		}
		
		StringBuilder builder = new StringBuilder();

		Map<String, String[]> map = req.getParameterMap();
		for (String key : map.keySet()) {

			String[] values = map.get(key);
			for(String value : values) {
				builder.append(key);
				builder.append("=");
				builder.append(value);
			}
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		String currentURI = uri;
		String definitionName = (String)req.getAttribute("os.rabbit.tiles.uri");
		
		if(definitionName != null) {
			currentURI = definitionName;

		}
		//System.out.println(definitionName);
		if (builder.length() > 0)
			if (currentURI.indexOf("?") == -1) {

				currentURI = currentURI + "?" + builder.toString();
			} else {

				currentURI = currentURI + "&" + builder.toString();
			}
		
		return currentURI;
	}

	private void updateValue(final IKeyValueProvider keyValueProvider, WebPage page) throws UnsupportedEncodingException {

		page.visit(new IComponentVisitor() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean visit(Component component) {
				if (component instanceof FormComponent) {
					FormComponent formCmp = (FormComponent) component;

					if (keyValueProvider.get(formCmp.getId()) != null) {
						formCmp.update();
					}
				}
				return true;
			}
		});
	}

}
