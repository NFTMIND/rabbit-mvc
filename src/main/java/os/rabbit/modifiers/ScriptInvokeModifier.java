package os.rabbit.modifiers;

import java.util.Collections;
import java.util.List;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.components.Component;

public class ScriptInvokeModifier extends URLInvokeModifier {

	public ScriptInvokeModifier(Component component, String name, ITrigger trigger) {
		super(component, name, trigger);
	
		
	}

	@Override
	protected ICallback buildCallback(Component component, ITrigger trigger) {
		return new ScriptInvokeCallback(component.getPage(), trigger);
	}

	public void setConfirm(String message) {
		if (getCallback() instanceof ScriptInvokeCallback) {
			((ScriptInvokeCallback) getCallback()).setConfirm(message);
		}
	}


	public List<Component> getUpdateComponentList() {
		if (getCallback() instanceof ScriptInvokeCallback) {

			return ((ScriptInvokeCallback) getCallback()).getUpdateComponents();
		}
		return Collections.EMPTY_LIST;
	}

	public void addUpdateComponent(Component cmp) {

		if (getCallback() instanceof ScriptInvokeCallback) {
			((ScriptInvokeCallback) getCallback()).addUpdateComponent(cmp);
		}
	}
	
}
