package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Label;
import os.rabbit.components.form.ComboBox;
import os.rabbit.parser.Tag;

public class ComboBoxDemo extends Component {

	private Form form;
	private Label msg;
	private ComboBox comboBox;
	public ComboBoxDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void afterBuild() {
		form.addFormListener(new IFormListener() {
			
			@Override
			public void submit() {
				msg.setValue(comboBox.getValue());
			}
		});
	}
	
	@Override
	protected void beforeRender() {

		comboBox.addOptions("選項1", "OP_1");
		comboBox.addOptions("選項2", "OP_2");
		comboBox.addOptions("選項3", "OP_3");
		comboBox.addOptions("選項4", "OP_4");
	
	}

}
