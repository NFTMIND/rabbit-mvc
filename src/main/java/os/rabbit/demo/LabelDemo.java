package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.parser.Tag;

public class LabelDemo extends Component {
	private Label label;
	public LabelDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		label.setValue("Hello world !!");
	}
	
}
