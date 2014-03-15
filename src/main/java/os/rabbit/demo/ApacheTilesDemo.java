package os.rabbit.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import org.apache.tiles.Definition;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.servlet.ServletApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;

import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class ApacheTilesDemo extends Component {

	public ApacheTilesDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void beforeRender() {
		ServletApplicationContext ac = new ServletApplicationContext(getPage().getRequest().getSession().getServletContext());
		

		ServletRequest req = new ServletRequest(ac, getPage().getRequest(), getPage().getResponse());

		String uri = (String)getPage().getRequest().getAttribute("javax.servlet.forward.servlet_path");
		uri = uri.substring(1, uri.length());
		TilesContainer container = TilesAccess.getCurrentContainer(req);
		Definition definition = container.getDefinition(uri, req);
		if(definition != null) {
			
		}
//		try {
//			container.render(definition.getAttribute("body"), req);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//container.render("apache_tiles_demo.tiles1", req);
	//	TilesContainer container = TilesAccess.getContainer( );
	//	System.out.println(container);
	//	container.render("myapp.homepage", getPage().getRequest().getSession(), response);
		
		
//	
//		for(Enumeration<String> enums = getPage().getRequest().getAttributeNames(); enums.hasMoreElements(); ) {
//			String name = enums.nextElement();
//			System.out.println(name + " : " + getPage().getRequest().getAttribute(name));
//		}
	}


}
