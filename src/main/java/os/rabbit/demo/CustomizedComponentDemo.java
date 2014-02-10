package os.rabbit.demo;

import java.io.PrintWriter;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class CustomizedComponentDemo extends Component {
	private AjaxInvokeCallback callback;

	public CustomizedComponentDemo(Tag tag) {
		super(tag);
	
	}
	@Override
	protected void afterBuild() {
		callback = new AjaxInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {
				setAttribute("myState", "I am invoked");
				CustomizedComponentDemo.this.repaint();

			}
		});
	}


	@Override
	public void renderComponent(PrintWriter writer) {
		String value = "Click me";
		if(getAttribute("myState") != null) {
			value = (String)getAttribute("myState");
		}
		writer.write("<input id=\"" + getId() + "\" type=\"button\" value=\"" + value + "\" onclick=\"alert('helloWorld');");
		callback.render(writer);
		writer.write("\" />");
	}

}
