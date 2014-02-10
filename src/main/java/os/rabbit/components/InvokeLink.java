package os.rabbit.components;

import java.util.LinkedList;

import os.rabbit.ITrigger;
import os.rabbit.modifiers.ScriptInvokeModifier;
import os.rabbit.modifiers.URLInvokeModifier;
import os.rabbit.parser.Tag;

public class InvokeLink extends Component implements ITrigger {
	private LinkedList<IButtonListener> listeners = new LinkedList<IButtonListener>();
	private URLInvokeModifier modifier;

	public InvokeLink(Tag tag) {
		super(tag);

		modifier = new URLInvokeModifier(this, "href", this);

	}

	public void setURI(String uri) {
		modifier.setURI(uri);
	}

	public void setConfirm(String message) {
		if (modifier instanceof ScriptInvokeModifier) {

			ScriptInvokeModifier scriptModifier = (ScriptInvokeModifier) modifier;
			scriptModifier.setConfirm(message);
		}
	}

	@Override
	public void afterBuild() {
		getPage().addTrigger(getId(), this);
	}

	public void addButtonListener(IButtonListener listener) {
		listeners.add(listener);
	}

	@Override
	public void invoke() {
		for (IButtonListener listener : listeners) {
			listener.click();
		}
	}

	/**
	 * 增新參數
	 * 
	 * @param key
	 * @param value
	 */
	public void setCallbackParameter(String key, String value) {
		modifier.setCallbackParameter(key, value);
	}

	/**
	 * 移除參數
	 * 
	 * @param key
	 */
	public void removeCallbackParameter(String key) {
		modifier.removeCallbackParameter(key);

	}



}
