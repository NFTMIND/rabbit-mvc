package os.rabbit.demo;

import java.io.PrintWriter;
import java.io.StringWriter;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.callbacks.URLInvokeCallback;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.components.IButtonListener;
import os.rabbit.parser.Tag;

public class CallbackDemo extends Component {
	private ELComponent code;
	private Button urlBtn;
	private Button scriptBtn;
	private Button ajaxBtn;

	public CallbackDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		final URLInvokeCallback urlInvokeCallback = new URLInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		final ScriptInvokeCallback scriptInvokeCallback = new ScriptInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		final AjaxInvokeCallback ajaxInvokeCallback = new AjaxInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		urlBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				urlInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
		scriptBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				scriptInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
		ajaxBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				ajaxInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
	}

	@Override
	protected void beforeRender() {
		
	}

}
