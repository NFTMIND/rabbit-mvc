package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;

public class AjaxButtonDemo extends Component {
	private AjaxButton button;
	public AjaxButtonDemo(Tag tag) {
		super(tag);
	}
	@Override
	protected void afterBuild() {
		button.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				getPage().executeScript("alert(\"AJAX配合Javascript執行!!\")");
			}
		});
	}
}
