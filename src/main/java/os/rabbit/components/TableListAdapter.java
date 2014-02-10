package os.rabbit.components;

import java.io.PrintWriter;
import java.util.List;

import os.rabbit.parser.Tag;

public class TableListAdapter<T> implements IListListener<T> {
	private ComponentList<T> component;

	public TableListAdapter(ComponentList<T> component) {
		this.component = component;
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

				writer.write("<tr><td align=\"center\" colspan=\"" + tds.size() + "\">"+component.transalte("目前尚無資料", component.getSelectedLocale())+"</td></tr>");

			}

			
		}
	}

}
