package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class TextBox extends FormComponent<String> {

	public TextBox(Tag tag) {
		super(tag);

		IRender valueRender = new IRender() {
			@Override
			public void render(PrintWriter writer) {
				renderValue(writer);
			}

		};
		if (tag.getName().equalsIgnoreCase("TEXTAREA")) {
			new BodyModifier(this, valueRender);
		} else {
			new AttributeModifier(this, "value", valueRender);
		}
	}
	
	protected void renderValue(PrintWriter writer) {
		Object value = getValue();
		if (value != null) {
			writer.write(value.toString());
		}
	}
	

	@Override
	protected String transform(Object value) {
		return (String)value;
	}

}
