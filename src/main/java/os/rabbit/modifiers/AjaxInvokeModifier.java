package os.rabbit.modifiers;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.components.Component;

public class AjaxInvokeModifier extends ScriptInvokeModifier {
	public AjaxInvokeModifier(Component component, String name, final ITrigger trigger) {
		super(component, name, trigger);
	}
	@Override
	protected ICallback buildCallback(Component component, ITrigger trigger) {
		return new AjaxInvokeCallback(component.getPage(), trigger);
	}

	public void setURI(String url) {
		ICallback callback = getCallback();
		callback.setURI(url);
		
		
	}
	public void setLoadingScreen(boolean value) {
		ICallback callback = getCallback();
		if(callback instanceof AjaxInvokeCallback) {
			((AjaxInvokeCallback)callback).setLoadingScreen(value);
		}
	}

}
