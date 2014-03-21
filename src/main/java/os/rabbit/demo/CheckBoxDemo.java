package os.rabbit.demo;

import java.util.List;

import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.form.CheckBox;
import os.rabbit.components.form.CheckBoxGroup;
import os.rabbit.parser.Tag;

public class CheckBoxDemo extends Component {

	private Label msg;
	private Form form;
	private CheckBoxGroup group;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private ListBuffer list;
	private CheckBox forEachCkbx;
	
	
	public CheckBoxDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void afterBuild() {
		form.addFormListener(new IFormListener() {
			
			@Override
			public void submit() {
				List<String> list = group.getValue();
				StringBuffer buf = new StringBuffer();
				for(String value : list) {
					buf.append(value + ",");
				}
				msg.setValue(buf.toString());
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		checkBox1.setValue("Checkbox 1");
		checkBox2.setValue("Checkbox 2");
		checkBox3.setValue("Checkbox 3");
		
		for(int i = 0; i < 10; i++) {
			forEachCkbx.setValue("forEachCkbx" + i);
			list.flush();
		}
	}

	
}
