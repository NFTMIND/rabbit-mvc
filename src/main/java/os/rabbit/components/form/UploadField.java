package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.UploadFile;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class UploadField extends FormComponent<UploadFile> {

	public UploadField(Tag tag) {
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
	protected UploadFile transform(Object value) {
		return (UploadFile)value;
	}
	
	@Override
	public void update() {
		UploadFile value = (UploadFile)getPage().getParameterObject(getId());

		setValue(value);
		
	}

}
