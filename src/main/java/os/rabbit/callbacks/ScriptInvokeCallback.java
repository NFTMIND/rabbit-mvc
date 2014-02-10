package os.rabbit.callbacks;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.StringRender;
import os.rabbit.components.Component;
import os.rabbit.components.WebPage;

public class ScriptInvokeCallback extends URLInvokeCallback {
	private LinkedList<Component> updateComponents = new LinkedList<Component>();
	private IRender msgRender;
	public ScriptInvokeCallback(WebPage page, ITrigger trigger) {
		super(page, trigger);
	}
	public ScriptInvokeCallback(InvokeType type, WebPage page, ITrigger trigger) {
		super(type, page, trigger);
	}




	//private String message;
	
//	public ScriptInvokeCallback(Component component, ITrigger trigger) {
//		super(component, trigger);
//
//	}



	@Override
	protected void renderValue(PrintWriter writer, Map<String, Object> parameters) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		for (Component component : updateComponents) {
			//if(component == getComponent()) {
			//	param.put(component.getId(), "'+$(this).fieldValue()+'");
			//} else {
				param.put(component.getId(), "'+$('*[name=" + component.getId() + "]').fieldValue()+'");
			//}
		}
		param.putAll(parameters);
		
		if(msgRender != null) {
			writer.write("if(confirm('");
			msgRender.render(writer);
			writer.write("')) {");
			renderScript(writer, param);
			writer.write("}");
		} else {
			renderScript(writer, param);
		}

	}
	protected void renderURL(PrintWriter writer, HashMap<String, Object> params) {
		super.renderValue(writer, params);
	}
	
	protected void renderScript(PrintWriter writer, HashMap<String, Object> params) {
		writer.write("location.href='");
		renderURL(writer, params);
		writer.write("';");
	}
	
	public void setConfirm(String message) {
		msgRender = new StringRender(getPage(), message);
	}
	public void setConfirm(IRender render) {
		msgRender = render;
	}
//	public StringRender getConfirmMessageRender() {
//		return msgRender;
//	}
//	public String getConfirmMessage() {
//		if(msgRender == null) return null;
//		return msgRender.getValue();
//	}

	public List<Component> getUpdateComponents() {
		return updateComponents;
	}
	
	public void addUpdateComponent(Component cmp) {
		updateComponents.add(cmp);
		
	}
	public void addThisUpdateComponent() {

		
	}
}
