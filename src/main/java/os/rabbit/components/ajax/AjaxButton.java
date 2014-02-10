package os.rabbit.components.ajax;

import java.lang.reflect.Field;
import java.util.LinkedList;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ComponentAdapter;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.UpdateComponent;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.modifiers.ScriptInvokeModifier;
import os.rabbit.parser.Tag;

public class AjaxButton extends Button {
	private LinkedList<IButtonListener> listeners = new LinkedList<IButtonListener>();

	private AjaxInvokeModifier modifier;
	private Field getField(Class c, String name) {
		try {
			return c.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public AjaxButton(Tag tag) {
		super(tag);


	}

	@Override
	protected ScriptInvokeModifier createModifier() {
		modifier = new AjaxInvokeModifier(this, "onclick", this);
		return modifier;
	}
	public void setLoadingScreen(boolean b) {
		modifier.setLoadingScreen(b);
	}


//	@Override
//	public void afterBuild() {
//		getPage().addTrigger(getId(), this);
//	}

//	public void addButtonListener(IButtonListener listener) {
//		listeners.add(listener);
//	}

//	@Override
//	public void invoke() {
//		for (IButtonListener listener : listeners) {
//			listener.click();
//		}
//	}


//
//	public void addUpdateComponent(Component cmp) {
//		modifier.addUpdateComponent(cmp);
//	}
//
//
//	public List<Component> getUpdateComponentList() {
//		return modifier.getUpdateComponentList();
//	}
//
//
//	public void removeCallbackParameter(String key) {
//		modifier.removeCallbackParameter(key);
//	}
//
//
//	public void setCallbackParameter(String key, String value) {
//		modifier.setCallbackParameter(key, value);
//	}
	
	
}
