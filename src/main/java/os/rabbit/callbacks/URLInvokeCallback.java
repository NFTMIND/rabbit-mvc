package os.rabbit.callbacks;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import os.rabbit.ITrigger;
import os.rabbit.components.WebPage;
import os.rabbit.modifiers.ICallback;

public class URLInvokeCallback implements ICallback {
	private String id;
	private String uri;
	//private Component component;
	private InvokeType type;
	private WebPage page;
	
	public URLInvokeCallback(InvokeType type, WebPage page, final ITrigger trigger) {
		this.page = page;
		this.type = type;
		id = page.addTrigger(trigger);
	}
	
	public URLInvokeCallback(WebPage page, final ITrigger trigger) {
		this(InvokeType.INVOKE, page, trigger);
	}
	
//	public URLInvokeCallback(Component component, final ITrigger trigger) {
//		this.component = component;
//		if (component.getPage() == null) {
//			component.addComponentListener(new IComponentListener() {
//				public void afterBuild() {
//					
//					id = URLInvokeCallback.this.component.getPage().addTrigger(trigger);
//				}
//
//				@Override
//				public void afterRender() {
//				}
//
//				@Override
//				public void beforeRender() {
//				}
//
//			});
//		} else {
//			id = component.getPage().addTrigger(trigger);
//		}
//	}

	public String getId() {
		return id;
	}

	protected void renderValue(PrintWriter writer, Map<String, Object> parameters) {

		StringBuffer urlBuffer = new StringBuffer();
		for (Object key : parameters.keySet()) {
			Object value = parameters.get(key);

			if (value instanceof String[]) {
				String[] params = (String[]) value;
				for (String v : params) {
					urlBuffer.append("&");
					urlBuffer.append(key);
					urlBuffer.append("=");
					urlBuffer.append(v);
				}
			} else {
				urlBuffer.append("&");
				urlBuffer.append(key);
				urlBuffer.append("=");
				urlBuffer.append(value);
			}
		}

		String value = "?rbtType="+type+"&triggerId=" + getId() + urlBuffer;
		if (uri != null) {
			writer.write(uri + value);
		} else {
			writer.write(value);
		}

	}

	protected void renderValue(PrintWriter writer) {
		Map<String, Object> parameterMap = getParameterMap();
		renderValue(writer, parameterMap);
	}



	public void setURI(String uri) {
		this.uri = uri;
	}

	@Override
	public void render(PrintWriter writer) {
		renderValue(writer);
	}
	
	public WebPage getPage() {
		return page;
	}

//	public Component getComponent() {
//		return component;
//	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> getParameterMap() {
		HttpServletRequest request = getPage().getRequest();
		Map<String, Object> parameterMap = (Map<String, Object>) request.getAttribute(id + "_param");
		if (parameterMap == null) {
			parameterMap = new HashMap<String, Object>();
			request.setAttribute(id + "_param", parameterMap);
		}
		return parameterMap;
	}

	/**
	 * 增新參數
	 * 
	 * @param key
	 * @param value
	 */
	public void setCallbackParameter(String key, String value) {
		Map<String, Object> parameterMap = getParameterMap();
		parameterMap.put(key, value);
	}

	public void setCallbackParameter(String key, String[] value) {
		Map<String, Object> parameterMap = getParameterMap();
		parameterMap.put(key, value);
	}

	/**
	 * 移除參數
	 * 
	 * @param key
	 */
	public void removeCallbackParameter(String key) {
		Map<String, Object> parameterMap = getParameterMap();
		parameterMap.remove(key);

	}

}
