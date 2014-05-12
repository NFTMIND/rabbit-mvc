package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.parser.Tag;

public class ELDemo extends Component {

	private ELComponent bean;
	private ELComponent label;

	public ELDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
	
		label.setValue("Text test");
		DemoObject el = new DemoObject();
	
		el.setAge(31);
		el.setName("Teco Li");
		el.setSex("Male");
		bean.setValue(el);
	
	}

}
