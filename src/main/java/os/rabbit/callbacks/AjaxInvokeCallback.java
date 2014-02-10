package os.rabbit.callbacks;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.WebPage;
import os.rabbit.components.form.FormComponent;

public class AjaxInvokeCallback extends ScriptInvokeCallback {
	//private Component component;
	//private String id;
	private boolean loadingScreen = false;
	public AjaxInvokeCallback(WebPage page, ITrigger trigger) {
		super(InvokeType.AJAX_INVOKE, page, trigger);
		// TODO Auto-generated constructor stub
	}

	
//
//	public AjaxInvokeCallback(final Component component, final ITrigger trigger) {
//		super(component, trigger);
//
//	}
//	



	protected void renderData(PrintWriter writer) {
		StringBuffer parameters = new StringBuffer();
	
		for (Component component : getUpdateComponents()) {
			if(component instanceof FormComponent) {
				FormComponent fc = (FormComponent)component;
				parameters.append("'");
				parameters.append(fc.getName());
				parameters.append("':");

			} else {
				parameters.append("'");
				parameters.append(component.getId());
				parameters.append("':");
			}

			parameters.append("$('*[rid=" + component.getRenderId() + "]').fieldValue()");
	
			parameters.append(",");
		}
		Map<String, Object> parameterMap = getParameterMap();
		for (String key : parameterMap.keySet()) {
			parameters.append("'");
			parameters.append(key);
			parameters.append("':");
			Object value = parameterMap.get(key);
			parameters.append("'");
			parameters.append(value);
			parameters.append("'");
			
			parameters.append(",");
		}

		if (parameters.length() > 0) {
			parameters.deleteCharAt(parameters.length() - 1);
			parameters.insert(0, "{");
			parameters.append("}");
		} else {
			parameters.append("null");
		}
		writer.write(parameters.toString());
	}
	
	public void setLoadingScreen(boolean value) {
		loadingScreen = value;
	}
	public boolean isLoadingScreen() {
		return loadingScreen;
	}


	@Override
	protected void renderScript(PrintWriter writer, HashMap<String, Object> params) {
		//StringRender render = getConfirmMessageRender();
//		if(render != null) {
//			writer.write("if(confirm('"); 
//			render.render(writer);
//			writer.write("')) {");
//			writer.write("Rabbit.invoke('");
//			writer.write(getPage().getRequestURI());
//			writer.write("','");
//			writer.write(getId());
//			writer.write("',");
//			renderData(writer);
//			writer.write(", "+loadingScreen+");");
//			
			//writer.write("}");
//		} else {
			writer.write("Rabbit.invoke('");
			writer.write(getPage().getRequestURI());
			writer.write("','");
			writer.write(getId());
			writer.write("',");
			renderData(writer);
			writer.write(", "+loadingScreen+");");
//		}
	}

	public void setThisObjectCallbackParameter(String key) {
		Map<String, Object> parameterMap = getParameterMap();
		parameterMap.put(key, "'+$(this).fieldValue()+'");
	}
	

}
