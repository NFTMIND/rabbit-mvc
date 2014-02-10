package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class FloatBox extends FormComponent<Float> {

	public FloatBox(Tag tag) {
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
	protected Float transform(Object value) {
		if(value == null) return null;
		return Float.parseFloat(value.toString());
	}
}
	
	
	
