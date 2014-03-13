package os.rabbit.tiles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;

import os.rabbit.components.Component;
import os.rabbit.parser.Tag;
/**
 * @deprecated
 * @author Teco
 *
 */
public class URLInsertBodyComponent extends Component {

	public URLInsertBodyComponent(Tag tag) {
		super(tag);

	}

	@Override
	public void renderComponent(final PrintWriter writer) {

		// RabbitTilesDefinition definition = (RabbitTilesDefinition)
		// getPage().getRequest().getAttribute(RabbitTilesServlet.RABBIT_TILES_DEFINITION);

		String extension = getTag().getAttribute("rabbit:extension");
		// String value =
		// definition.getAttributeValue(getTag().getAttribute("rabbit:id"));
		HttpServletRequest req = getPage().getRequest();
		String resourcePath = req.getRequestURI().replace(req.getContextPath(), "");

		String resourcePathHead = resourcePath;
		int dotPos = resourcePath.lastIndexOf(".");
		if (dotPos != -1) {
			resourcePathHead = resourcePath.substring(0, dotPos);
		}

		RequestDispatcher reqDispatcher = req.getRequestDispatcher(resourcePathHead + "." + extension);
		final ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			try {
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
			} catch (Exception e) {
				e.printStackTrace(new PrintWriter(output));
			}
			writer.write(new String(output.toByteArray(), "utf-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
