package os.rabbit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import os.rabbit.components.WebPage;
import os.rabbit.parser.Tag;
import os.rabbit.parser.XMLParser;

public class PageFactory {
	private static final Logger logger = Logger.getLogger(PageFactory.class);

	private HttpServlet servlet;
	private HashMap<String, StorageWebPage> pageMap = new HashMap<String, StorageWebPage>();
	private String encoding = "utf-8";

	public PageFactory(HttpServlet servlet, String encoding) {
		this.servlet = servlet;
		this.encoding = encoding;
	
	
	}

	private StorageWebPage createWebPage(InputStream in, long lastModified) {
		try {

			XMLParser parser = new XMLParser(in, encoding);
			Tag tag = parser.parse();
			WebPage page = new WebPage(servlet, tag);

			// List<Component> pageInjectComps = page.getPageInjectComponents();
			// for (final Component cmp : pageInjectComps) {
			// final PageInject pageInject =
			// cmp.getClass().getAnnotation(PageInject.class);
			//
			// final StringBuffer newHTML = new StringBuffer();
			// // WebPage templatePage = get(pageInject.path());
			// String realTemplatePath = context.getRealPath(pageInject.path());
			// File templateFile = new File(realTemplatePath);
			// Tag templateRootTag = new XMLParser(templateFile,
			// encoding).parse();
			// newHTML.append(templateRootTag.getTemplate());
			// templateRootTag.visit(new ITagStructureVisitor() {
			// @Override
			// public void visit(Tag tag) {
			// if (tag.getAttribute("rabbit:id") != null &&
			// tag.getAttribute("rabbit:id").equals(pageInject.name())) {
			//
			// String content =
			// cmp.getTag().getTemplate().substring(cmp.getTag().getStart(),
			// cmp.getTag().getEnd());
			//
			// newHTML.replace(tag.getStart(), tag.getEnd(), content);
			// }
			// }
			// });
			// page = new WebPage(new XMLParser(new
			// Context(newHTML.toString())).parse());
			// }
			return new StorageWebPage(page, lastModified);
		} catch (IOException e) {
		}

		return null;
	}

	private StorageWebPage createWebPageForClasspath(String path) {

		try {
			URL url = getClass().getResource(path);
			if (url == null)
				return null;
			URLConnection connection = url.openConnection();
			long lastModified = connection.getLastModified();

			return createWebPage(connection.getInputStream(), lastModified);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private StorageWebPage createWebPage(String path) {
		try {
			String realPath = servlet.getServletContext().getRealPath(path);
			File file = new File(realPath);

			return createWebPage(new FileInputStream(file), file.lastModified());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public WebPage get(String path, Locale locale) {
		int dot = path.lastIndexOf(".");

		String pathWithoutExt = path.substring(0, dot);
		String ext = path.substring(dot, path.length());
		boolean fromClasspath = false;

		if (pathWithoutExt.endsWith("@")) {
			fromClasspath = true;
			pathWithoutExt = pathWithoutExt.substring(0, pathWithoutExt.length() - 1);
		}

		if (locale != null && dot != -1) {
			String temp = pathWithoutExt + "_" + locale + ext;
			if (fromClasspath) {

				URL url = getClass().getResource(temp);
				if (url != null) {
					pathWithoutExt += "_" + locale;
				}

			} else {

				String realPath = servlet.getServletContext().getRealPath(temp);

				File file = new File(realPath);
				if (file.exists()) {
					pathWithoutExt += "_" + locale;
				}
			}
		}
		path = pathWithoutExt + ext;

		StorageWebPage page = pageMap.get(path);
		if (page == null) {
			if (fromClasspath) {
				page = createWebPageForClasspath(path);
			} else {
				page = createWebPage(path);
			}
			pageMap.put(path, page);
		} else {
			boolean isModified = false;
			if (fromClasspath) {
				URL url = getClass().getResource(path);
			
				URLConnection connection = null;
				try {
					connection = url.openConnection();
					long lastModified = connection.getLastModified();
					isModified = lastModified != page.lastModified;
					if (isModified) {
						page = createWebPageForClasspath(path);
						pageMap.put(path, page);
						logger.info("The Web page of '" + path + "' had changed");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				String realPath = servlet.getServletContext().getRealPath(path);
				File file = new File(realPath);
				isModified = file.lastModified() != page.lastModified;
				if (isModified) {
					page = createWebPage(path);
					pageMap.put(path, page);
					logger.info("The Web page of '" + path + "' had changed");
				}
			}

		}
		if (page == null)
			return null;
		return page.page;
	}

	class StorageWebPage {
		public long lastModified;
		public WebPage page;

		public StorageWebPage(WebPage page, long lastModified) {
			this.page = page;
			this.lastModified = lastModified;
		}

	}
}
