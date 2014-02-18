package os.rabbit.components;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;

import os.rabbit.parser.Tag;

public class SpringBeanSupportComponent extends Component {


	public SpringBeanSupportComponent(Tag tag) {
		super(tag);

	}
//	public static WebApplicationContext getWebApplicatioonContext(HttpServletRequest req) {
//		return (WebApplicationContext) req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//	}
//	
	public static WebApplicationContext getWebApplicationContext(WebPage page) {
		return (WebApplicationContext) page.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}
	public WebApplicationContext getWebApplicatioonContext() {
		return getWebApplicationContext(getPage()); 
	}

	public Object getBean(String beanName) {
		WebApplicationContext context = getWebApplicatioonContext();
		if (context == null)
			throw new RuntimeException("The Spring Application Context is not exist, You have to config Spring initialize in web.xml");
		return context.getBean(beanName);
	}

}
