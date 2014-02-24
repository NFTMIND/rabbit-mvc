package os.rabbit.components;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import os.rabbit.IKeyValueProvider;
import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.IRequestCycleListener;
import os.rabbit.ITrigger;
import os.rabbit.RenderInterruptedException;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class WebPage extends Component {
	private static Logger logger = Logger.getLogger(WebPage.class);

	class HeadAppendModifier implements IModifier {
		private Range range;

		public HeadAppendModifier(Tag tag) {
			range = new Range(tag.getClosedSignStart(), tag.getClosedSignStart());
		}

		@Override
		public Range getRange() {
			return range;
		}

		@Override
		public void render(PrintWriter writer) {
			renderHead(writer);
		}
	}

	// private LinkedList<Component> pageInjectComponents = new
	// LinkedList<Component>();
	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	private HashMap<String, ITrigger> triggerTable = new HashMap<String, ITrigger>();
	private HashMap<String, IRender> scripts = new HashMap<String, IRender>();
	private HashMap<String, IRender> scriptImports = new HashMap<String, IRender>();
	private HashMap<String, IRender> cssImports = new HashMap<String, IRender>();
	private ServletContext context;
	public WebPage(ServletContext context, final Tag tag) {
		super(tag);
		this.context = context;
		initChildrenComponent(this, this, tag);
		reverseVisit(new IComponentVisitor() {

			@Override
			public boolean visit(Component component) {

				component.buildComplete();
				return true;
			}
		});
		final Tag headTag = findHead(tag);
		if (headTag != null) {
			final Tag scriptOrLinkOrStyleTag = findScriptOrLinkOrStyleTag(headTag);
			if (scriptOrLinkOrStyleTag == null) {
				addModifier(new HeadAppendModifier(headTag));
			} else {
				addModifier(new IModifier() {

					@Override
					public void render(PrintWriter writer) {
						renderHead(writer);
					}

					@Override
					public Range getRange() {
						// TODO Auto-generated method stub
						return new Range(scriptOrLinkOrStyleTag.getStart(), scriptOrLinkOrStyleTag.getStart());
					}
				});
			}
		} else {

			addModifier(new IModifier() {

				@Override
				public void render(PrintWriter writer) {
					renderHead(writer);
				}

				@Override
				public Range getRange() {
					// TODO Auto-generated method stub
					return new Range(tag.getBodyStart(), tag.getBodyStart());
				}
			});

		}

	}
	public ServletContext getServletContext() {
		return context;
	}

	public Tag findScriptOrLinkOrStyleTag(Tag tag) {

		if (tag.getName().equalsIgnoreCase("LINK") || tag.getName().equalsIgnoreCase("SCRIPT") || tag.getName().equalsIgnoreCase("STYLE")) {

			return tag;
		}

		for (Tag child : tag.getChildrenTags()) {
			Tag returnTag = findScriptOrLinkOrStyleTag(child);
			if (returnTag != null) {
				return returnTag;
			}
		}
		return null;
	}

	public Tag findHead(Tag tag) {

		if (tag.getName().equalsIgnoreCase("HEAD")) {

			return tag;
		}

		for (Tag child : tag.getChildrenTags()) {
			Tag returnTag = findHead(child);
			if (returnTag != null) {
				return returnTag;
			}
		}
		return null;
	}

	@Override
	protected void beforeRender() {
		String contextPath = getRequest().getContextPath();
		String uri = getRequest().getRequestURI();
		String curRes = uri.substring(contextPath.length(), uri.length());

		StringBuffer relativelyRoot = new StringBuffer(".");

		for (int loop = 1; loop < curRes.length(); loop++) {
			if (curRes.charAt(loop) == '/') {
				relativelyRoot.append("/..");
			}
		}
		setAttribute("relativelyRoot", relativelyRoot.toString());
	}

	public String getRelativelyRoot() {
		return (String) getAttribute("relativelyRoot");
	}

	protected void renderHead(PrintWriter writer) {

		for (IRender render : cssImports.values()) {
			writer.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
			render.render(writer);
			writer.println("\" />");

		}

		// String contextPath = getRequest().getContextPath();
		writer.println("<script src=\"" + getRelativelyRoot() + "/rbt/jquery-1.8.3.min.js\"></script>");
		writer.println("<script src=\"" + getRelativelyRoot() + "/rbt/rabbit.js\"></script>");

		for (IRender render : scriptImports.values()) {
			writer.print("<script src=\"");
			render.render(writer);
			writer.println("\"></script>");

		}

		if (scripts.size() > 0) {
			writer.println("<script>");

			for (IRender render : scripts.values()) {
				render.render(writer);
			}
			writer.println("</script>");

		}
	}

	public void addCSSImport(String name, final String url) {
		cssImports.put(name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(url);
			}
		});
	}

	public void addCSSImport(String name, IRender urlRender) {
		cssImports.put(name, urlRender);
	}

	public void addScriptImport(String name, IRender urlRender) {
		scriptImports.put(name, urlRender);
	}

	public void addScriptImport(String name, final String url) {
		scriptImports.put(name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(url);
			}
		});
	}

	public void addScript(String name, IRender script) {
		scripts.put(name, script);
	}

	private void initChildrenComponent(Component container, Component parent, Tag tag) {
		initChildrenComponent("", container, parent, tag);
	}

	/**
	 * 初始化所有子元件 如果遇到Tag內含"rabbit:class"屬性 則自動產生新元件，並將Tag內的所有子元件納入該元件內
	 * 
	 * @param container
	 * @param tag
	 */
	private void initChildrenComponent(String space, Component container, Component parent, Tag tag) {
		for (Tag eachTag : tag.getChildrenTags()) {

			String rabbitId = eachTag.getAttribute("rabbit:id");
	
			String rabbitClass = eachTag.getAttribute("rabbit:class");
			if (rabbitClass != null) {
				try {
				
					Component newParentComponent = createComponentByClassName(rabbitClass, eachTag);
					newParentComponent.setContainer(true);
					parent.addChild(newParentComponent);

				
					initChildrenComponent(space + "	", newParentComponent, newParentComponent, eachTag);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				if (rabbitId != null) {

					Component newComponent = buildComponent(container, parent, rabbitId, eachTag);
					boolean isContainer = newComponent != null && newComponent.getClass().getAnnotation(RabbitContainer.class) != null;
					if (newComponent != null) {
						newComponent.setContainer(isContainer);
					}
					if (isContainer) {
						initChildrenComponent(space + "	", newComponent, newComponent, eachTag);
					} else {
						initChildrenComponent(space + "	", container, newComponent, eachTag);
					}
				} else {
					initChildrenComponent(space + "	", container, parent, eachTag);
				}
			}
		}
	}

	/**
	 * 依照類別名稱創建子元件
	 * 
	 * @param className
	 * @param tag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Component createComponentByClassName(String className, Tag tag) throws Exception {

		Class<Component> targetClass;
		try {
			targetClass = (Class<Component>) Class.forName(className);

			Constructor<Component> constructor = targetClass.getConstructor(Tag.class);
			return constructor.newInstance(tag);
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	private Field getField(Class<? extends Component> c, String name) {
		Field field = null;
		try {
			field = c.getDeclaredField(name);

		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		}
		return field;
	}

	private Field searchField(Class<? extends Component> c, String name) {
		Field field = getField(c, name);
		if (field == null) {
			Class<? extends Component> sc = (Class<? extends Component>) c.getSuperclass();
			RabbitContainer container = sc.getAnnotation(RabbitContainer.class);
			if (container != null) {
				field = searchField(sc, name);
			}

		}
		return field;
	}

	/**
	 * 依RabbitId尋找對應父的元件內是否有相同名稱欄位，如果有就自動初始化元件
	 * 
	 * @param container
	 * @param name
	 * @param tag
	 * @return
	 */
	private Component buildComponent(Component container, Component parent, String name, Tag tag) {
		Class<? extends Component> classInstance = container.getClass();
		try {

			Field field = searchField(classInstance, name);

			if (field == null) {
				logger.warn("field \""+name+"\" couldn't be found.");
				return null;
			}

			Class<?> fieldClass = field.getType();
			Constructor<?> mappedConstructor = fieldClass.getConstructor(Tag.class);
			Component mappedComponent = (Component) mappedConstructor.newInstance(tag);
			field.setAccessible(true);
			field.set(container, mappedComponent);

			parent.addChild(mappedComponent);
			return mappedComponent;

		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	private ThreadLocal<Writer> writer = new ThreadLocal<Writer>();

	private LinkedList<IRequestCycleListener> rclList = new LinkedList<IRequestCycleListener>();

	public void addRequestCycleListener(IRequestCycleListener listener) {
		rclList.add(listener);
	}

	public void requestStart(HttpServletRequest request, HttpServletResponse response) {
		this.request.set(request);
		this.response.set(response);
		writer.set(new StringWriter());
		for (IRequestCycleListener rcl : rclList) {
			rcl.requestStart(request, response);
		}
	}

	public void requestEnd() {
		for (IRequestCycleListener rcl : rclList) {
			rcl.requestEnd(request.get(), response.get());
		}
		this.request.remove();
		this.response.remove();

	}

	public Writer getWriter() {
		return writer.get();
	}

	public HttpServletRequest getRequest() {
		return request.get();
	}

	public HttpServletResponse getResponse() {
		return response.get();
	}

	public Object getParameterObject(String name) {
		IKeyValueProvider keyValueProvider = (IKeyValueProvider) getRequest().getAttribute("KEY_VALUE_PROVIDER");
		return keyValueProvider.get(name);
	}

	public String getParameter(String name) {
		Object object = getParameterObject(name);
		if (object == null)
			return null;
		if (object instanceof String) {

			String value = (String) object;
			if (value.trim().length() == 0) {
				return null;
			}
			return value;
		}

		return null;
	}

	public Integer getParameterAsInteger(String name) {
		String value = getParameter(name);

		if (value != null) {
			if (value.trim().length() == 0) {
				return null;
			}
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Double getParameterAsDouble(String name) {
		String value = getParameter(name);

		if (value != null) {
			if (value.trim().length() == 0) {
				return null;
			}
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Float getParameterAsFloat(String name) {
		String value = getParameter(name);

		if (value != null) {
			if (value.trim().length() == 0) {
				return null;
			}
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public Long getParameterAsLong(String name) {
		String value = getParameter(name);

		if (value != null) {
			if (value.trim().length() == 0) {
				return null;
			}
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public ITrigger getTrigger(String triggerId) {
		return triggerTable.get(triggerId);
	}

	private int triggerIndex = 1;

	public String addTrigger(ITrigger trigger) {
		String triggerId = "t" + triggerIndex;

		triggerTable.put(triggerId, trigger);

		triggerIndex++;

		return triggerId;
	}

	public void addTrigger(String id, ITrigger trigger) {
		triggerTable.put(id, trigger);
	}

	// public List<Component> getPageInjectComponents() {
	// return pageInjectComponents;
	// }

	// public void setRequestURI(String uri) {
	// getPage().getRequest().setAttribute("RBT_REQ_URI", uri);
	// }

	public String getRequestURI() {
		return (String) getPage().getRequest().getAttribute("RBT_REQ_URI");
	}

	public void setRedirect(String url) {
		getRequest().setAttribute("RBT_REDIRECT_URL", url);
		//throw new RenderInterruptedException();
	}

	public String getRedirect() {
		if (getRequest() == null)
			return null;
		return (String) getRequest().getAttribute("RBT_REDIRECT_URL");
	}

	private LinkedList<IAuthorizationValidator> authorizationValidators;

	public void addAuthorizationValidator(IAuthorizationValidator validator) {
		if (authorizationValidators == null) {
			authorizationValidators = new LinkedList<IAuthorizationValidator>();
		}
		authorizationValidators.add(validator);
	}

	public boolean isAuthorized() {
		if (authorizationValidators != null) {
			for (IAuthorizationValidator validator : authorizationValidators) {
				boolean rs = validator.validate(this);
				if (rs == false)
					return false;
			}
		}
		return true;
	}

	public int generateRenderIndex() {

		Integer index = (Integer) getAttribute("renderIndex");
		if (index == null) {
			index = 1;
		} else {
			index++;
		}
		setAttribute("renderIndex", index);
		return index;
	}

}
