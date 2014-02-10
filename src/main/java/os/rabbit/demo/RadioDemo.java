package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Label;
import os.rabbit.components.form.Radio;
import os.rabbit.components.form.RadioGroup;
import os.rabbit.parser.Tag;

public class RadioDemo extends Component {

	private Label msg;
	private Form form;
	private RadioGroup group;
	private Radio radio1;
	private Radio radio2;
	private Radio radio3;
	
	public RadioDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void afterBuild() {

		form.addFormListener(new IFormListener() {
			
			@Override
			public void submit() {
				msg.setValue(group.getValue());
				System.out.println(group.getValue());
			}
		});
	}
	
	@Override
	protected void beforeRender() {

		radio1.setValue("Radio1");
		radio2.setValue("Radio2");
		radio3.setValue("Radio3");
	}

}
