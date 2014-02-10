package os.rabbit.demo;

import java.io.PrintWriter;
import java.util.LinkedList;

import os.rabbit.components.Component;
import os.rabbit.components.ComponentList;
import os.rabbit.components.IListListener;
import os.rabbit.components.Label;
import os.rabbit.parser.Tag;

public class ComponentListDemo extends Component {

	private ComponentList<String> list;
	private Label label;
	public ComponentListDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void afterBuild() {

		list.setListListener(new IListListener<String>() {
			
			@Override
			public void emptyRender(PrintWriter writer) {}
			
			@Override
			public void beforeEachRender(PrintWriter writer, String object, int index) {
				label.setValue(object);
			}
			
			@Override
			public void afterEachRender(PrintWriter writer, String object, int index) {}
		});
	}

	@Override
	protected void beforeRender() {
		LinkedList<String> data = new LinkedList<String>();
		data.add("清單1");
		data.add("清單2");
		data.add("清單3");
		data.add("清單4");
		
		list.setCollection(data);
		
	}


}
