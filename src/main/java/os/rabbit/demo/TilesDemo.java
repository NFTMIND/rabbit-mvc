package os.rabbit.demo;

import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.parser.Tag;


public class TilesDemo extends SpringBeanSupportComponent {

	public TilesDemo(Tag tag) {
		super(tag);

	}

	@Override
	protected void beforeRender() {
	//	throw new RuntimeException();
	}
}
