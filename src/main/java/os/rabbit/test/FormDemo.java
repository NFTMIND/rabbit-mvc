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
				System.out.println("123");
				// Enumeration<String> e =
				// getPage().getRequest().getAttributeNames();
				// String name = null;
				// while( e.hasMoreElements()){
				// name = e.nextElement();
				// System.out.println("name = "+name+", value = "+getPage().getRequest().getAttribute(name));
				// }
				System.out.println("auth = " + getPage().getRequest().getHeader("Authorization"));

				// String key = null;
				// Enumeration<String> e1
				// =getPage().getRequest().getHeaderNames();
				// while( e1.hasMoreElements()){
				// key = e1.nextElement();
				// System.out.println("name = "+key+", value = "+getPage().getRequest().getHeader(key));
				// }

				System.out.println("remotehost = " + getPage().getRequest().getRemoteHost());

				// formUser.error("測試");
				// getPage().setRedirect("http://tw.yahoo.com");
				// throw new RenderInterruptedException();

				// IUserService userService =
				// (IUserService)getBean("userService");
				// userService.test();
			}

		});

	}

}
