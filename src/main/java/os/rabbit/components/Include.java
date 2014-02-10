package os.rabbit.components;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponseWrapper;

import os.rabbit.IRender;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class Include extends Component {

	public Include(Tag tag) {
		super(tag);
		
		new BodyModifier(this, new IRender() {
			@Override
			public void render(final PrintWriter writer) {
				
				String include = (String)getAttribute("URI");
				RequestDispatcher reqDispatcher = getPage().getRequest().getRequestDispatcher(include);
				try {
					
					final ByteArrayOutputStream output = new ByteArrayOutputStream();
					HttpServletResponseWrapper resp = new HttpServletResponseWrapper(getPage().getResponse()) {
						 public PrintWriter getWriter() throws IOException {	 
							 return writer;
						 };
						 public javax.servlet.ServletOutputStream getOutputStream() throws IOException {
		 
							 return new ServletOutputStream() {
								
								@Override
								public void write(int b) throws IOException {
								
									output.write(b);
								}
							};
							 
						 };
						 
					};

					reqDispatcher.include(getPage().getRequest(), resp);
					writer.write(new String(output.toByteArray(), "utf-8"));
			
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setURI(String uri) {
		setAttribute("URI", uri);
	}

}
