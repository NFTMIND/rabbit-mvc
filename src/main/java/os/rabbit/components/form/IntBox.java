package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class IntBox extends FormComponent<Integer> {

	public IntBox(Tag tag) {
		super(tag);
		new AttributeModifier(this, "value", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				Object value = getValue();
				if(value != null) {
					
					writer.write(value.toString());
				}
			}
			
		});
	}

	@Override
	protected Integer transform(Object value) {
		if(value == null) return null;
		return Integer.parseInt(value.toString());
	}

}
