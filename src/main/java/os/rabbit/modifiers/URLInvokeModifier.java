package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.URLInvokeCallback;
import os.rabbit.components.Component;
import os.rabbit.parser.Range;

public class URLInvokeModifier implements IModifier {

	private AttributeModifier modifier;
	private Component component;
	private ICallback callback;

	public URLInvokeModifier(Component component, String name, final ITrigger trigger) {
		callback = buildCallback(component, trigger);

		this.modifier = new AttributeModifier(component, name, this);
		this.component = component;
	}
	
	protected ICallback buildCallback(Component component, ITrigger trigger) {
		return new URLInvokeCallback(component.getPage(), trigger);
	}
	public ICallback getCallback() {
		return callback;
	} 

	public String getId() {
		return callback.getId();
	}

	@Override
	public Range getRange() {
		return modifier.getRange();
	}


	public void setURI(String uri) {
		callback.setURI(uri);
	}

	@Override
	public void render(PrintWriter writer) {
		callback.render(writer);
	}

	public Component getComponent() {
		return component;
	}

	
	/**
	 * 增新參數
	 * 
	 * @param key
	 * @param value
	 */
	public void setCallbackParameter(String key, String value) {
		callback.setCallbackParameter(key, value);
	}
	public void setCallbackParameter(String key, String[] values) {
		callback.setCallbackParameter(key, values);
	}
	/**
	 * 移除參數
	 * 
	 * @param key
	 */
	public void removeCallbackParameter(String key) {
		callback.removeCallbackParameter(key);

	}
}
