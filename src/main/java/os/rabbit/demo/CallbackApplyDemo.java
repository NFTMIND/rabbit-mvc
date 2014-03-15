package os.rabbit.demo;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.components.form.TextBox;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class CallbackApplyDemo extends Component {
	private Label label;
	private TextBox input;
	private ScriptInvokeCallback callback;
	public CallbackApplyDemo(Tag tag) {
		super(tag);

	}
	
	@Override
	protected void initial() {
		callback = new ScriptInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				label.setValue(input.getValue());
			}
		});
		//if you added input component into callback, server side will get input value when callback method be triggered
		callback.addUpdateComponent(input);
		
		getPage().addScript("callbackApplyDemo", new IRender() {
			
			@Override
			public void render(PrintWriter writer) {
				writer.println("function invokeRemoteMethod() {");
			
				callback.render(writer);
				writer.println();
				writer.println("}");
				
				
			}
		});
		//The way is used to modify tag attribute
		new AttributeModifier(input, "onblur", "invokeRemoteMethod()");
	}
	
	@Override
	protected void beforeRender() {
		if(label.getValue() == null) {
			label.setValue("empty");
		}
	}

}
