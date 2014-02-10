package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.form.BooleanCheckBox;
import os.rabbit.parser.Tag;

public class AjaxCheckBoxDemo extends Component {

	private BooleanCheckBox checkbox;

	public AjaxCheckBoxDemo(Tag tag) {
		super(tag);

	}

	@Override
	protected void afterBuild() {
//		BooleanCheckBoxAjaxInvokeModifier modifier = new BooleanCheckBoxAjaxInvokeModifier(checkbox, "onclick", new ITrigger() {
//
//			@Override
//			public void invoke() {
//				getPage().executeScript("alert(\"變更屬性：" + checkbox.getValue() + "\")");
//			}
//		});

//		modifier.setConfirm("您是否確定要變更屬性？");
	}

}
