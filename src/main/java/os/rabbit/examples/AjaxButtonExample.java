package os.rabbit.examples;

import os.rabbit.components.Button;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class AjaxButtonExample extends SpringBeanSupportComponent {
	private Button button;
	private TextBox field;
	public AjaxButtonExample(Tag tag) {
		super(tag);

	}
	
	@Override
	protected void afterBuild() {
		button.addUpdateComponent(field);
		button.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
			
			}
		});
	}

}
