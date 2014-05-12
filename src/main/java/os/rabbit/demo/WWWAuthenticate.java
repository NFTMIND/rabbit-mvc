package os.rabbit.demo;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import os.rabbit.RenderInterruptedException;
import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class WWWAuthenticate extends Component {

	public WWWAuthenticate(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
	
		String au = getPage().getRequest().getHeader("Authorization");
		if(au != null) {
			au = au.substring(5, au.length());
		
			String rs = new String(Base64.decodeBase64(au));
		
		}
		
		getPage().getResponse().setHeader("WWW-Authenticate", "Basic realm=\"testrealm@keakon.cn\"");
		getPage().getResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//throw new RenderInterruptedException();
	}
	
}
