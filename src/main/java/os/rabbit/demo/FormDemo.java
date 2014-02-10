package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Label;
import os.rabbit.components.form.BooleanCheckBox;
import os.rabbit.components.form.CheckBox;
import os.rabbit.components.form.CheckBoxGroup;
import os.rabbit.components.form.Radio;
import os.rabbit.components.form.RadioGroup;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class FormDemo extends Component {
	private Label msg;
	private Form form;
	private TextBox txtBox;
	private BooleanCheckBox booleanCheckBox;

	private CheckBoxGroup checkBoxGroup;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;

	private RadioGroup radioGroup;
	private Radio radio1;
	private Radio radio2;
	private Radio radio3;

	public FormDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void afterBuild() {

		form.addFormListener(new IFormListener() {
			@Override
			public void submit() {

			}
		});
	}

}
