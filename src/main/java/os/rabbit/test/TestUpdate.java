package os.rabbit.test;

import os.rabbit.components.Form;
import os.rabbit.components.IFormListener;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.form.TextBox;
import os.rabbit.components.form.UploadField;
import os.rabbit.parser.Tag;

public class TestUpdate extends SpringBeanSupportComponent {
	private Form form;
	private TextBox textfield;
	private UploadField fileField;

	public TestUpdate(Tag tag) {
		super(tag);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void afterBuild() {
		form.addFormListener(new IFormListener() {

			@Override
			public void submit() {
				//System.out.println("file:" + fileField.getValue().length);
				//System.out.println("field:" + textfield.getValue());
			}
		});
	}

}
