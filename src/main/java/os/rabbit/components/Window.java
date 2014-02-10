package os.rabbit.components;

import java.io.PrintWriter;

import os.rabbit.modifiers.JSProxy;
import os.rabbit.parser.Tag;

public class Window extends Component {

	public Window(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
	}

	@Override
	public void renderComponent(PrintWriter writer) {

		super.renderComponent(writer);
		// writer.write("<script src=\"window.js\"></script>");

		writer.write("<script src=\"" + getPage().getRequest().getContextPath() + "/rbt/window.js\"></script>");
		writer.println("<script>");
		writer.println("var windowObj = new RWindow(\"" + getId() + "\", \"" + getPage().getRequest().getContextPath() + "/rbt\");");
		writer.println("windowObj.onclose = function() {");
		writer.println("	");
		writer.println("}");

		
		String triggerCloseId = getId() + "$ONCLOSE";
		
		JSProxy proxy = new JSProxy();
		proxy.invoke(getPage().getRequestURI(), triggerCloseId, null, false);
		
		
		writer.println("</script>");

	}
}
