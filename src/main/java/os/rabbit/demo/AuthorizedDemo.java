package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.WebPage;
import os.rabbit.parser.Tag;

public class AuthorizedDemo extends Component {
	private Button btnLogout;
	public AuthorizedDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		btnLogout.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				getPage().getRequest().getSession().removeAttribute("authorized");
				getPage().setRedirect("authorized_demo.tiles");
			}
		});
		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			
			@Override
			public boolean validate(WebPage page) {
				Boolean authorized = (Boolean)getPage().getRequest().getSession().getAttribute("authorized");
				return authorized != null && authorized;
			}
		});
	}
	
}
