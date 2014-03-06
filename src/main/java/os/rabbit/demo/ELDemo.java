package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.parser.Tag;

public class ELDemo extends Component {
	private ELComponent accessableELCmp;
	
	public ELDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		DemoObject el = new DemoObject();
		el.setAge(32);
		el.setName("Hello");
		el.setSex("男");
		accessableELCmp.setValue(el);
	}
	
}
