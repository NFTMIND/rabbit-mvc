package os.rabbit.test;

import os.rabbit.components.Form;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IFormListener;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.parser.Tag;

public class FormDemo extends SpringBeanSupportComponent {
	private Form formUser;

	public FormDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
	}

	@Override
	protected void initial() {
		super.initial();

		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			@Override
			public boolean validate(WebPage page) {
			
				return false;
			}
		});

		formUser.addFormListener(new IFormListener() {
			@Override
			public void submit() {
				
				
			}

		});

	}

}
