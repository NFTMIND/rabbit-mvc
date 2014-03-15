package os.rabbit.demo;

import os.rabbit.components.ELComponent;
import os.rabbit.parser.Tag;

public class ExtendLabelDemo extends LabelDemo {
	private ELComponent el;
	public ExtendLabelDemo(Tag tag) {
		super(tag);
		
	}
	
	@Override
	protected void beforeRender() {
		el.setValue("Hi");
	}

}
