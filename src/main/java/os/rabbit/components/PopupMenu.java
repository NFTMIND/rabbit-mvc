package os.rabbit.components;

import java.util.List;

import os.rabbit.IRender;
import os.rabbit.callbacks.PopupMenuItem;
import os.rabbit.callbacks.PopupMenuModifier;
import os.rabbit.parser.Tag;

public class PopupMenu extends Component {
	private PopupMenuModifier modifier;
	public PopupMenu(Tag tag) {
		super(tag);
		modifier = new PopupMenuModifier(this, "onclick");
		
	}
	
	public List<PopupMenuItem> getItems() {
		return  modifier.getItems();
	}
	
	
	
	public void addSeparator() {
		modifier.addSeparator();
	}
	
	public void addItem(final String name, final IRender render) {
		modifier.addItem(name, render);
	}
	public void addURLItem(final String name, final String url) {
		modifier.addURLItem(name, url);
	}

	
	public void clearItem() {
		modifier.clearItem();
	}
	
	public void removeItem(String name) {
		modifier.removeItem(name);
	}
}
