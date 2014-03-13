package os.rabbit.tiles;

import os.rabbit.parser.Tag;

public class InsertBodyComponent extends InsertAttributeComponent {

	public InsertBodyComponent(Tag tag) {
		super(tag);

	}

//	@Override
//	public void renderComponent(final PrintWriter writer) {
//		//System.out.println(1 + ":" + Calendar.getInstance().getTimeInMillis());
//		RabbitTilesDefinition definition = (RabbitTilesDefinition) getPage().getRequest().getAttribute(RabbitTilesServlet.RABBIT_TILES_DEFINITION);
//		
//		System.out.println("definition:" + definition);
//		
//		
//		String value = definition.getAttributeValue(getTag().getAttribute("rabbit:id"));
//		RequestDispatcher reqDispatcher = getPage().getRequest().getRequestDispatcher(value);
//
//		final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//		final StringWriter bufferWriter = new StringWriter();
//		final PrintWriter tmpWriter = new PrintWriter(bufferWriter);
//		try {
//			HttpServletResponseWrapper resp = new HttpServletResponseWrapper(getPage().getResponse()) {
//				
//				@Override
//				public PrintWriter getWriter() throws IOException {
//					return tmpWriter;
//				}
//			
//				@Override
//				public void flushBuffer() throws IOException {
//			
//				}
//				@Override
//				public void setHeader(String name, String value) {
//				
//					getPage().getResponse().setHeader(name, value);
//				}
//				@Override
//				public void setStatus(int sc) {
//	
//					getPage().getResponse().setStatus(sc);
//				}
//				@Override
//				public void sendError(int sc, String msg) throws IOException {
//					getPage().getResponse().sendError(sc, msg);
//				}
//				@Override
//				public void sendError(int sc) throws IOException {
//					getPage().getResponse().sendError(sc);
//				}
//
//				@Override
//				public ServletOutputStream getOutputStream() throws IOException {
//			
//					return new ServletOutputStream() {
//
//						@Override
//						public void write(int value) throws IOException {
//							byteArray.write(value);
//					
//						}
//
//						@Override
//						public void write(byte[] b, int off, int len) throws IOException {
//							byteArray.write(b, off, len);
//							System.out.println("write");
//						}
//						@Override
//						public void flush() throws IOException {
//							byteArray.flush();
//						}
//
//						@Override
//						public void write(byte[] b) throws IOException {
//						
//							write(b,0,b.length);
//						}
//						
//						
//					};
//				}
//			};
//	
//			
//			reqDispatcher.include(getPage().getRequest(), resp);
//		
//			byteArray.write(bufferWriter.toString().getBytes("utf-8"));
//			
//			String htmlContent = new String(byteArray.toByteArray(), "utf-8");
//		
//			writer.write(htmlContent);
//		
//		} catch (Exception e1) {
//			throw new RuntimeException(e1);
//		}
//
//	}

}
