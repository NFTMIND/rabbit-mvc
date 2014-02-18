package os.rabbit.components;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import os.rabbit.Help;
import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.modifiers.RemoveAttributeModifier;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;
import os.rabbit.parser.XMLParser;

public class Component implements IModifier {

	private LinkedList<Component> childrens = new LinkedList<Component>();
	private LinkedList<IModifier> modifiers = new LinkedList<IModifier>();

	private LinkedList<IComponentListener> componentListenerList = new LinkedList<IComponentListener>();

	private Component parent;
	private Tag tag;
	private Range range;

	private String id;
	private static int serial;



	public Component(Tag tag) {
		this.tag = tag;

		range = new Range(tag.getStart(), tag.getEnd());
		id = tag.getAttribute("rabbit:id");
		if (id == null) {
			id = generateId();
		}
		new RemoveAttributeModifier(this, "rabbit:id");
		new RemoveAttributeModifier(this, "rabbit:class");
		new AttributeModifier(this, "id", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getId());
			}
		});

		new AttributeModifier(this, "rId", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.print(getRenderId());
			}
		});
		if (tag.getAttribute("rabbit:translation") != null) {

			// attribute:value,title
			// body
			//
	
			String translation = tag.getAttribute("rabbit:translation");
			if (translation.startsWith("attribute")) {
				String sources = translation.substring(10, translation.length());
				String[] attrs = sources.split(",");
				for (final String attr : attrs) {
					new AttributeModifier(this, attr, new IRender() {
						@Override
						public void render(PrintWriter writer) {
							String value = getTag().getAttribute(attr);
							if (value != null) {
								String rs = transalte(getTag().getAttribute(attr), getLocale());
								if (rs != null) {
									writer.write(rs);
								} else {
									writer.write(value);
								}
							}
						}
					});
				}
			} else if (translation.startsWith("body")) {
				new BodyModifier(this, new IRender() {
					@Override
					public void render(PrintWriter writer) {
						if (getTag().hasBody()) {
							String body = getTag().getTemplate().substring(getTag().getBodyStart(), getTag().getBodyEnd());

							String rs = transalte(body, getLocale());
							if (rs != null) {
								writer.write(rs);
							} else {
								writer.write(body);
							}
						}

					}
				});

			}
		}

		addComponentListener(new ComponentAdapter() {
			Method methodBeforeRender;
			Method methodInitial;
			Method methodAfterRender;

			@Override
			public void beforeRender() {
				if (methodBeforeRender != null) {
					try {
						methodBeforeRender.setAccessible(true);
						methodBeforeRender.invoke(getContainer());
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

			@Override
			public void afterRender() {
				if (methodAfterRender != null) {
					try {
						methodAfterRender.setAccessible(true);
						methodAfterRender.invoke(getContainer());
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

			@Override
			public void initial() {
				final Component container = getContainer();
				if (container != null) {
					Class<? extends Component> c = container.getClass();
					methodBeforeRender = Help.getMethod(c, getId() + "$onBeforeRender");
					methodInitial = Help.getMethod(c, getId() + "$onInitial");
					methodAfterRender = Help.getMethod(c, getId() + "$onAfterRender");
					if (methodInitial != null) {
						try {
							methodInitial.setAccessible(true);
							methodInitial.invoke(container);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private static HashMap<String, HashMap<String, String>> languageWordMap = new HashMap<String, HashMap<String, String>>();

	public String getLocale() {

		String selectedLocale = (String) getPage().getRequest().getSession().getAttribute("locale");
		if (selectedLocale == null) {
			selectedLocale = getPage().getRequest().getLocale().toString();
		}
		return selectedLocale;

	}

	private boolean container;

	public void setContainer(boolean value) {
		container = value;
	}

	public boolean isContainer() {
		return container;
	}

	public Component getContainer() {
		Component parent = getParent();
		while (parent != null) {
			if (parent.isContainer()) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	public String getRenderId() {

		int v = getRenderIndex();

		return "r" + v;
	}

	public int getRenderIndex() {
		Integer returnVal = (Integer) getAttribute("renderIndex");
		if (returnVal == null) {
			returnVal = getPage().generateRenderIndex();
			setAttribute("renderIndex", returnVal);
		}

		return returnVal;
	}

	public Map<String, String> getLanguageTable(String language) {
		HashMap<String, String> table = languageWordMap.get(language);
		if (table == null) {
			String filePath = getPage().getRequest().getSession().getServletContext().getRealPath("/WEB-INF/languages/" + language + ".xml");
			table = new HashMap<String, String>();
			File file = new File(filePath);
			if (file.exists()) {
				try {
					XMLParser parser = new XMLParser(filePath, "utf-8");
					Tag root = parser.parse();

					for (Tag tag : root.getChildrenTags()) {
						int start = tag.getBodyStart();
						int end = tag.getBodyEnd();
						String target = tag.getTemplate().substring(start, end);
						String value = tag.getAttribute("value");

						table.put(value, target);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			languageWordMap.put(language, table);
		}

		return table;
	}

	public void save(String locale) {

		String dir = getPage().getRequest().getSession().getServletContext().getRealPath("/WEB-INF/languages");
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		String filePath = getPage().getRequest().getSession().getServletContext().getRealPath("/WEB-INF/languages/" + locale + ".xml");

		try {
			PrintWriter writer = new PrintWriter(filePath, "utf-8");

			Map<String, String> table = getLanguageTable(locale);

			writer.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			writer.println("<language>");
			for (String key : table.keySet()) {

				writer.println("	<translation value=\"" + key + "\">" + table.get(key) + "</translation>");

			}
			writer.println("</language>");
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String transalte(String word) {
		return transalte(word, getLocale());
	}

	public String transalte(String word, String targetLanguage) {
		
		String filePath = getPage().getRequest().getSession().getServletContext().getRealPath("/WEB-INF/languages/" + targetLanguage + ".xml");

		Map<String, String> map = getLanguageTable(targetLanguage);

		String translation = map.get(word);
		if (translation != null) {

			return translation;
		} else {
			if(true) return word;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			int dash = targetLanguage.indexOf("_");
			String l = targetLanguage;
			if (dash != -1) {
				l = targetLanguage.substring(0, dash);
			}
			try {

				HttpGet httpGet = new HttpGet("http://translate.google.com.tw/translate_a/t?client=t&text=" + URLEncoder.encode(word, "utf-8") + "&hl=zh-TW&sl=zh-CN&tl=" + l
						+ "&multires=1&srcrom=1&prev=btn&ssel=0&tsel=0&sc=1");

				httpGet.setHeader("Accept-Encoding", "deflate");

				httpGet.setHeader("Accept", "text/html, application/xhtml+xml, */*");

				httpGet.setHeader("Referer", "http://translate.google.com.tw/?hl=zh-TW");
				httpGet.setHeader("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
				httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7");
				httpGet.setHeader("Cookie", "PREF=ID=f14fb0e4f8255eab:U=11dedb46572cc47c:FF=0:TM=1307442385:LM=1323618666:S=559J_D_4yq4fwfd_; NID=54=eY7ogrfGpn3OhXedrO9BwSd52b1x3ts");
				httpGet.setHeader("Host", "translate.google.com.tw");
				httpGet.setHeader("Connection", "Keep-Alive");

				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();

				InputStream in = entity.getContent();

				ByteArrayOutputStream temp = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				while (true) {
					int len = in.read(buffer);
					if (len == -1)
						break;
					temp.write(buffer, 0, len);

				}
				temp.flush();
				temp.close();
				String value = new String(temp.toByteArray(), "utf-8");
				int end = value.indexOf("\"", 4);
				value = value.substring(4, end);

				map.put(word, value);
				save(targetLanguage);

				return value;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	private HashMap<String, AttributeModifier> attributeModifierMap = new HashMap<String, AttributeModifier>();

	public void setAttribute(String name, Object value) {
		getPage().getRequest().setAttribute(getId() + "_ATTR$" + name, value);
	}

	public Object getAttribute(String name) {
		return getPage().getRequest().getAttribute(getId() + "_ATTR$" + name);
	}

//	private void setTagAttributeModifier(final String name, boolean on) {
//		if (on) {
//			if (!attributeModifierMap.containsKey(name)) {
//				
//			}
//		} else {
//			AttributeModifier modifier = attributeModifierMap.remove(name);
//			removeModifier(modifier);
//		}
//	}

	public void setTagAttribute(final String name, String value) {
		if(!attributeModifierMap.containsKey(name)) {
			AttributeModifier modifier = new AttributeModifier(this, name, new IRender() {
				@Override
				public void render(PrintWriter writer) {
					Object value = getPage().getRequest().getAttribute("RBT_ATTR_MODIFIER$" + getId() + "$" + name);
					if (value != null) {

						writer.write(value.toString());
					}
				}

			}) {
				@Override
				protected void renderAttribute(String name, IRender valueRender, PrintWriter writer) {
					Object value = getPage().getRequest().getAttribute("RBT_ATTR_MODIFIER$" + getId() + "$" + name);
					if (value != null) {
						super.renderAttribute(name, valueRender, writer);
					}
				}
			};
			attributeModifierMap.put(name, modifier);
		}
		
		
		if (value != null)
			getPage().getRequest().setAttribute("RBT_ATTR_MODIFIER$" + getId() + "$" + name, value);
		else
			getPage().getRequest().removeAttribute("RBT_ATTR_MODIFIER$" + getId() + "$" + name);
	}

	private BodyModifier bodyModifier = null;

	public void setTagBodyModifier(boolean on) {
		if (on) {
			if (bodyModifier == null) {

				bodyModifier = new BodyModifier(this, new IRender() {

					@Override
					public void render(PrintWriter writer) {
						String value = (String) getPage().getRequest().getAttribute("RBT_BODY_MODIFIER$" + getId());
						if (value != null) {
							writer.write(value);
						} else {
							writer.write(getTag().getBody());
						}
					}
				});
			}
		} else {
			if (bodyModifier != null) {
				removeModifier(bodyModifier);
				bodyModifier = null;
			}
		}
	}

	public void setTagBody(String value) {
		getPage().getRequest().setAttribute("RBT_BODY_MODIFIER$" + getId(), value);
	}

	public void setId(String id) {

		this.id = id;
	}

	public static String generateId() {
		return "cmp_" + serial++;
	}

	public String getId() {
		return id;
	}

	public WebPage getPage() {
		Component running = this;
		while (running != null) {
			if (running instanceof WebPage) {

				return (WebPage) running;
			}
			running = running.getParent();
		}
		return null;
	}

	public Component getParent() {
		return parent;
	}

	public void addChild(Component component) {
		childrens.add(component);
		if (component.getTag().getTemplate() == getTag().getTemplate()) {
			addModifier(component);
		}

		component.parent = this;
	}

	public List<Component> getChildrens() {
		return new ArrayList<Component>(childrens);
	}

	public void removeChild(Component component) {
		modifiers.remove(component);
		removeModifier(component);
	}

	public void addModifier(IModifier modifier) {
		modifiers.add(modifier);

		Collections.sort(modifiers, new Comparator<IModifier>() {
			@Override
			public int compare(IModifier o1, IModifier o2) {
				if (o1.getRange().getStart() < o2.getRange().getStart())
					return -1;
				if (o1.getRange().getStart() > o2.getRange().getStart())
					return 1;
				return 0;
			}
		});
	}

	public void removeModifier(IModifier modifier) {
		modifiers.remove(modifier);
	}

	public void buildComplete() {
		for (IComponentListener listener : componentListenerList) {
			listener.afterBuild();
			listener.initial();
		}
		afterBuild();
		initial();
	}

	private Point findPosition(String template, int position) {
		int line = 0;
		int offset = 0;
		for (int loop = 0; loop < position; loop++) {
			if (template.charAt(loop) == '\n') {
				line++;
				offset = 0;
			} else {
				offset++;
			}
		}
		return new Point(offset, line);
	}

	public void renderComponent(PrintWriter writer) {
		String template = tag.getTemplate();
		int runningPos = range.getStart(); // 目前的起點
		for (IModifier modifier : modifiers) {

			Range eachRange = modifier.getRange();

			if (eachRange.getStart() < runningPos) {

				Point a = findPosition(template, runningPos);
				Point b = findPosition(template, eachRange.getStart());
				StringBuilder builder = new StringBuilder();

				builder.append("line " + b.y + "~" + a.y + "...." + template.substring(eachRange.getStart(), runningPos) + "....");
				throw new RuntimeException(builder.toString());

			}
			writer.write(template.substring(runningPos, eachRange.getStart()));
			modifier.render(writer);
			runningPos = eachRange.getEnd();
		}
		writer.write(template.substring(runningPos, range.getEnd()));
	}

	protected void renderScript(PrintWriter writer) {

	}

	@Override
	public void render(PrintWriter writer) {
		if (isVisible()) {
			getRenderIndex();



	
			for (IComponentListener listener : componentListenerList) {
				listener.beforeRender();
			}

			beforeRender();
			if (isVisible()) {
				renderComponent(writer);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				renderScript(pw);

				String script = sw.toString();
				if (script.length() > 0) {
					writer.println("<script>");

					writer.print(sw.toString());

					writer.println("</script>");
				}
			} else {
				writer.write("<span id=\"" + getId() + "\" />");
			}
			afterRender();
		
			for (IComponentListener listener : componentListenerList) {
				listener.afterRender();
			}
			
			
			setAttribute("renderIndex", getPage().generateRenderIndex());
		

		} else {
			writer.write("<span id=\"" + getId() + "\" />");
		}
	}

	public void repaint() {
		PrintWriter writer = new PrintWriter(getPage().getWriter());
		writer.write("<html id=\"" + getId() + "\">");
		writer.write("<![CDATA[");
		render(writer);
		writer.write("]]>");
		writer.write("</html>");

		writer.write("<script>");
		writer.write("<![CDATA[");
		renderScript(writer);
		writer.write("]]>");
		writer.write("</script>");
	}

	public void executeScript(String code) {
		PrintWriter writer = new PrintWriter(getPage().getWriter());
		writer.write("<script>");
		writer.write("<![CDATA[");
		writer.write(code);
		writer.write("]]>");
		writer.write("</script>");
	}

	@Override
	public Range getRange() {
		return range;
	}

	public void visit(IComponentVisitor visitor) {
		visitor.visit(this);
		for (Component children : new ArrayList<Component>(childrens)) {

			children.visit(visitor);

		}
	}

	public void reverseVisit(IComponentVisitor visitor) {

		for (Component children : new ArrayList<Component>(childrens)) {

			children.reverseVisit(visitor);

		}
		visitor.visit(this);
	}

	public Tag getTag() {
		return tag;
	}

	public void addComponentListener(IComponentListener listener) {
		componentListenerList.add(listener);
	}

	/**
	 * 子類別繼承後可覆寫的方法
	 */
	@Deprecated
	protected void afterBuild() {

	}

	/**
	 * 子類別繼承後可覆寫的方法
	 */
	protected void initial() {
	}

	protected void beforeRender() {
	}

	protected void afterRender() {
	}

	public void setVisible(boolean visible) {
		getPage().getRequest().setAttribute(getId() + "_VISIBLE", visible);
	}

	public boolean isVisible() {
		Boolean result = (Boolean) getPage().getRequest().getAttribute(getId() + "_VISIBLE");
		return result == null || result;
	}

	public List<IModifier> getModifiers() {
		return modifiers;
	}

}
