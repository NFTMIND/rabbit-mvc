package os.rabbit.demo;

import java.util.Enumeration;

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
		//javax.servlet.forward.request_uri
		//javax.servlet.forward.servlet_path
//		Enumeration<String> enums = getPage().getRequest().getAttributeNames();
//		while(enums.hasMoreElements()) {
//			String key = enums.nextElement();
//			System.out.println(key + " : " + getPage().getRequest().getAttribute(key));
//		}
//		String uri = (String) getPage().getRequest().getAttribute("UNAUTHORIZED_URI");
		
		backURL.setValue((String)getPage().getRequest().getAttribute("javax.servlet.forward.request_uri"));
	}

}
