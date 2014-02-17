package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class LoginDemo extends Component {
	private TextBox backURL;
	private Button btnLogin;
	public LoginDemo(Tag tag) {
		super(tag);
	}
	@Override
	protected void initial() {
		btnLogin.addUpdateComponent(backURL);
		btnLogin.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				getPage().getRequest().getSession().setAttribute("authorized", Boolean.TRUE);
				getPage().setRedirect(backURL.getValue());
				
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		String uri = (String) getPage().getRequest().getAttribute("UNAUTHORIZED_URI");
		backURL.setValue(uri);
	}

}
