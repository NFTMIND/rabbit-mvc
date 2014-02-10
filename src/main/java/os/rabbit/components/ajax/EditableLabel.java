package os.rabbit.components.ajax;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.components.Component;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class EditableLabel extends FormComponent<String> {
	private LinkedList<IUpdateListener> listeners = new LinkedList<IUpdateListener>();
	private String emptyString = "";
	private AjaxInvokeCallback callback;


	public EditableLabel(Tag tag) {
		super(tag);
		
	}



	@Override
	protected void afterBuild() {
		callback = new AjaxInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {
				for (IUpdateListener listener : listeners) {
					listener.update(getValue());
				}
			}
		});

		// callback.addUpdateComponent(this);
		new AttributeModifier(this, "ondblclick", new IRender() {
			@Override
			public void render(PrintWriter writer) {

				writer.write("toEditableTextField('" + getRenderId() + "', function(text) {");
				callback.render(writer);

				writer.write("});");

			}
		});

		new BodyModifier(this, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				if (getValue() != null) {
					writer.write(getValue());
				} else {

					writer.write(emptyString);
				}
			}
		});
		getPage().addScript("EDITABLE_LABEL", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.println("function onEditComplete(eventObject) {");
				writer.println("	var field = $(eventObject.target);");
				writer.println("	var jObject = $(eventObject.target).data('object');");

				writer.println("	jObject.css(\"display\", \"block\");");
				writer.println("	var value = field.val();");
				writer.println("	if(value.trim().length <= 0) {");
				writer.println("		jObject.html(\"" + emptyString + "\");");
				writer.println("	} else {");
				writer.println("		jObject.html(value);");
				writer.println("	}");
				writer.println("	field.remove();");
				writer.println("	callback(value);");
				writer.println("}");

				writer.println("function toEditableTextField(rId, callback) {");
				writer.println("	var jObject = $(\"*[rId=\"+rId+\"]\");");

				writer.println("	var field = $(\"<input type=\\\"text\\\" />\");");

				writer.println("	if(jObject.html() == \"" + emptyString + "\") {");
				writer.println("		field.val(\"\");");
				writer.println("	} else {");
				writer.println("		field.val(jObject.html());");
				writer.println("	}");

				writer.println("	jObject.after(field);");
				writer.println("	field.data('object',jObject);");
				writer.println("	field.focus();");

				writer.println("	jObject.css(\"display\", \"none\");");
				writer.println("	field.blur(onEditComplete);");
				writer.println("	field.keyup(function(e) {");

				writer.println("		if(e.keyCode == 13)");
				writer.println("			onEditComplete(e)");

				writer.println("	});");

				writer.println("}");
			}
		});
	}

	@Override
	protected void beforeRender() {
		callback.setCallbackParameter(getId(), "'+text+'");
	}
	public String getEmptyString() {
		return emptyString;
	}

	public void setEmptyString(String emptyString) {
		this.emptyString = emptyString;
	}

	
	@Override
	protected String transform(Object value) {
		return value.toString();
	}

	public List<Component> getUpdateComponents() {
		return callback.getUpdateComponents();
	}

	public void addUpdateComponent(Component cmp) {
		callback.addUpdateComponent(cmp);
	}

	public void removeCallbackParameter(String key) {
		callback.removeCallbackParameter(key);
	}

	public void setCallbackParameter(String key, String value) {
		callback.setCallbackParameter(key, value);
	}

	public void addUpdateListener(IUpdateListener listener) {
		listeners.add(listener);
	}

}
