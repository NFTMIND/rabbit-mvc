package os.rabbit.demo;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.components.form.TextBox;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.parser.Tag;

public class LabelDemo extends Component {
	private Label label;

	
	public LabelDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {

	}

	@Override
	protected void beforeRender() {
		label.setValue(translate("Hello world !!"));

	}
	
}
