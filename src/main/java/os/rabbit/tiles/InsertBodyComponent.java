package os.rabbit.tiles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponseWrapper;

import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class InsertBodyComponent extends Component {

	public InsertBodyComponent(Tag tag) {
		super(tag);

	}

	@Override
	public void renderComponent(final PrintWriter writer) {
		//System.out.println(1 + ":" + Calendar.getInstance().getTimeInMillis());
		RabbitTilesDefinition definition = (RabbitTilesDefinition) getPage().getRequest().getAttribute(RabbitTilesServlet.RABBIT_TILES_DEFINITION);
		String value = definition.getAttributeValue(getTag().getAttribute("rabbit:id"));
		RequestDispatcher reqDispatcher = getPage().getRequest().getRequestDispatcher(value);

		final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		final StringWriter bufferWriter = new StringWriter();
		final PrintWriter tmpWriter = new PrintWriter(bufferWriter);
		try {
			HttpServletResponseWrapper resp = new HttpServletResponseWrapper(getPage().getResponse()) {
				
				@Override
				public PrintWriter getWriter() throws IOException {
					return tmpWriter;
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
						public void write(byte[] b) throws IOException {

							byteArray.write(b);
						}
					};
				}
			};
			
			reqDispatcher.include(getPage().getRequest(), resp);

			byteArray.write(bufferWriter.toString().getBytes("utf-8"));
			
			String htmlContent = new String(byteArray.toByteArray(), "utf-8");
		
			writer.write(htmlContent);
			
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}

	}

}
