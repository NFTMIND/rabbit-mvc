package os.rabbit.tiles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.servlet.ServletApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;

import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class InsertAttributeComponent extends Component {

	public InsertAttributeComponent(Tag tag) {
		super(tag);
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		ServletApplicationContext ac = new ServletApplicationContext(getPage().getRequest().getSession().getServletContext());
		final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		final StringWriter bufferWriter = new StringWriter();
		final PrintWriter tmpWriter = new PrintWriter(bufferWriter);
		
		HttpServletResponseWrapper resp = new HttpServletResponseWrapper(getPage().getResponse()) {
			
			@Override
			public PrintWriter getWriter() throws IOException {
				return tmpWriter;
			}
		
			@Override
			public void flushBuffer() throws IOException {
		
			}
			@Override
			public void setHeader(String name, String value) {
			
				getPage().getResponse().setHeader(name, value);
			}
			@Override
			public void setStatus(int sc) {

				getPage().getResponse().setStatus(sc);
			}
			@Override
			public void sendError(int sc, String msg) throws IOException {
				getPage().getResponse().sendError(sc, msg);
			}
			@Override
			public void sendError(int sc) throws IOException {
				getPage().getResponse().sendError(sc);
			}

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
		
				return new ServletOutputStream() {

					@Override
					public void write(int value) throws IOException {
						byteArray.write(value);
				
					}

					@Override
					public void write(byte[] b, int off, int len) throws IOException {
						byteArray.write(b, off, len);
					
					}
					@Override
					public void flush() throws IOException {
						byteArray.flush();
					}

					@Override
					public void write(byte[] b) throws IOException {
					
						write(b,0,b.length);
					}
					
					
				};
			}
		};
		ServletRequest req = new ServletRequest(ac, getPage().getRequest(), resp);

		String uri = (String)getPage().getRequest().getAttribute("javax.servlet.forward.servlet_path");
		uri = uri.substring(1, uri.length());
		if(uri.endsWith(".tiles")) {
			uri = uri.substring(0, uri.length() - 6);
		}
		
		TilesContainer container = TilesAccess.getCurrentContainer(req);
		Definition definition = container.getDefinition(uri, req);
		
		
	
		if(definition != null) {
			Attribute attr = definition.getAttribute(getId());


	
			try {
				
		
				container.render(attr, req);
			
				byteArray.write(bufferWriter.toString().getBytes("utf-8"));
				
				String htmlContent = new String(byteArray.toByteArray(), "utf-8");
			
				writer.write(htmlContent);
			
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}
}
