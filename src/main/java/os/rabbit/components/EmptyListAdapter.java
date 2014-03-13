package os.rabbit.components;

import java.io.PrintWriter;
import java.util.List;

import os.rabbit.components.ComponentList;
import os.rabbit.components.IListListener;
import os.rabbit.parser.Tag;

public class EmptyListAdapter<T> implements IListListener<T> {
	private ComponentList<T> component;
	private String emptyMessage = "目前尚無資料";
	public EmptyListAdapter(ComponentList<T> component) {
		this.component = component;
		component.setListListener(this);
		
	}
	
	public void setEmptyMessage(String value) {
		this.emptyMessage = value;
	}

	@Override
	public void beforeEachRender(PrintWriter writer, T object, int index) {

	}

	@Override
	public void afterEachRender(PrintWriter writer, T object, int index) {

	}

	@Override
	public void emptyRender(PrintWriter writer) {
		String tagName = component.getTag().getName().toUpperCase();
		if (tagName.equals("TR")) {
			List<Tag> tds = component.getTag().getChildrenTags();
			if (tds.size() > 0) {
				writer.write("<tr><td align=\"center\" colspan=\"" + tds.size() + "\">"+component.translate(emptyMessage, component.getLocale())+"</td></tr>");
			}

			
		}
	}

}
