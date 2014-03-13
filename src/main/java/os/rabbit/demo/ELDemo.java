package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.components.ListBuffer;
import os.rabbit.parser.Tag;

public class ELDemo extends Component {
	private ListBuffer list;
	private ELComponent abc;
	private ELComponent other;

	public ELDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
	
		other.setValue("中文測試");
		DemoObject el = new DemoObject();
		for (int i = 0; i < 5; i++) {
			el.setAge(32 + i);
			el.setName("Hello");
			el.setSex("男");
			abc.setValue(el);
			list.flush();
		}
	}

}
