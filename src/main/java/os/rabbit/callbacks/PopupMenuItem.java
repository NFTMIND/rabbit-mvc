package os.rabbit.callbacks;

import os.rabbit.IRender;

public class PopupMenuItem {
	public String name;
	public IRender callbackRender;
	public PopupMenuItem(String name, IRender callbackRender) {
		this.name = name;
		this.callbackRender = callbackRender;
	}
}
