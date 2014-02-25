package os.rabbit.tiles;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import os.rabbit.parser.Tag;
import os.rabbit.parser.XMLParser;

public class RabbitTilesServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
		updateTilesDefinition();
	}

	private HashMap<String, RabbitTilesDefinition> definitionMap = new HashMap<String, RabbitTilesDefinition>();
	private long lastModified;
	private void updateTilesDefinition() {
		definitionMap.clear();
		String definitionConfigPaths = getServletConfig().getInitParameter("definitions-config");
		String[] definitionConfigPathArray = definitionConfigPaths.split(",");
		for(String definitionConfigPath : definitionConfigPathArray) {
			File file = new File(getServletContext().getRealPath(definitionConfigPath));

			try {
				lastModified = file.lastModified();
				XMLParser parser = new XMLParser(file, "utf-8");
				Tag root = parser.parse();

				for (Tag tag : root.getChildrenTags()) {
					if (tag.getName().equals("definition")) {
						String name = tag.getAttribute("name");
						String template = tag.getAttribute("template");

						if (template != null) {
							RabbitTilesDefinition definition = new RabbitTilesDefinition(name, template);

							for (Tag child : tag.getChildrenTags()) {
								String attrName = child.getAttribute("name");
								String attrValue = child.getAttribute("value");
								definition.setAttribute(attrName, attrValue);
							}
							definitionMap.put(definition.getName(), definition);
						}
					}
				}

				for (Tag tag : root.getChildrenTags()) {
					if (tag.getName().equals("definition")) {
						String name = tag.getAttribute("name");
						String extendsDefinition = tag.getAttribute("extends");
						if (extendsDefinition != null) {

							RabbitTilesDefinition extendedDefinition = definitionMap.get(extendsDefinition);

							if (extendedDefinition != null) {
							
								RabbitTilesDefinition definition = new RabbitTilesDefinition(name, extendedDefinition.getTemplate());

								List<String> attrList = extendedDefinition.getAttributeNameList();
								for (String attrName : attrList) {
									extendedDefinition.setAttribute(attrName, extendedDefinition.getAttributeValue(attrName));
								}

								for (Tag child : tag.getChildrenTags()) {
									String attrName = child.getAttribute("name");
									String attrValue = child.getAttribute("value");
									definition.setAttribute(attrName, attrValue);
								}
								definitionMap.put(definition.getName(), definition);
							}
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

	public static final String RABBIT_TILES_DEFINITION = "RABBIT_TILES_DEFINITION";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String definitionConfigPath = getServletConfig().getInitParameter("definitions-config");

		File file = new File(getServletContext().getRealPath(definitionConfigPath));
		if(file.lastModified() != lastModified) {
			
			updateTilesDefinition();
		}
		String uri = req.getRequestURI();
		String resourcePath = uri.substring(req.getContextPath().length(), uri.length());
		req.setAttribute("os.rabbit.tiles.uri", uri);
		RabbitTilesDefinition def = definitionMap.get(resourcePath);

		if (def != null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(def.getTemplate());
			req.setAttribute("os.rabbit.uri", uri);
			
			req.setAttribute(RABBIT_TILES_DEFINITION, def);
			
			dispatcher.forward(req, resp);
			
			resp.flushBuffer();
			//System.out.println("tiles end");
		} else {
			resp.sendError(404, "not found definition of '" + resourcePath + "'");
		}
	}
}
