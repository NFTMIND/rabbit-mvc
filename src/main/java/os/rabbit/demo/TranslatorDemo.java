package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class TranslatorDemo extends Component {
	private ELComponent result;
	private TextBox input;
	private Button btnTranslate;
	public TranslatorDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {
		btnTranslate.addUpdateComponent(input);
		btnTranslate.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				result.setValue(translate(input.getValue()));
			}
		});
	}

	
}
