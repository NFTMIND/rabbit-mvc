package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.WebPage;
import os.rabbit.parser.Tag;

public class AuthorizedDemo extends Component {

	public AuthorizedDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			
			@Override
			public boolean validate(WebPage page) {
				Boolean authorized = (Boolean)getPage().getRequest().getSession().getAttribute("authorized");
				return authorized != null && authorized;
			}
		});
	}
	
}
