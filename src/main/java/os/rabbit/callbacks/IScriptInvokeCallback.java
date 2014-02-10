package os.rabbit.callbacks;

import os.rabbit.components.Component;
import os.rabbit.modifiers.ICallback;

public interface IScriptInvokeCallback extends ICallback {

	public void addUpdateComponent(Component component);
}
