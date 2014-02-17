package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class ButtonDemo extends Component {
	private Label label;
	private TextBox input;
	private Button button;
	public ButtonDemo(Tag tag) {
		super(tag);
	}
	@Override
	protected void initial() {
		button.addUpdateComponent(input);
	}
	
	@Override
	protected void afterBuild() {
		button.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				label.setValue(input.getValue());
			}
		});
	}
}
