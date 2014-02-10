package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class Radio extends FormComponent<String> {

	public Radio(Tag tag) {
		super(tag);
	
	}

	@Override
	protected String transform(Object value) {
		return (String)value;
	}
	
	@Override
	protected void initial() {
		RadioGroup group = getGroup();
		if (group != null) {
			setName(group.getId());
		}
		
		IRender valueRender = new IRender() {
			@Override
			public void render(PrintWriter writer) {
				Object value = getValue();
				if (value != null) {

					writer.write(value.toString());
				}
			}

		};
		new AttributeModifier(this, "value", valueRender);
		new AttributeModifier(this, "checked", "checked") {
			@Override
			protected void renderAttribute(String name, IRender valueRender, PrintWriter writer) {
				String value = getValue();
				if (value != null) {
					RadioGroup group = getGroup();
					if (group != null) {
						String selectedValue = group.getValue();
						if (selectedValue != null) {
							if (selectedValue.equals(value)) {
								super.renderAttribute(name, valueRender, writer);
							}
						}
					}
				}
			};
		};
	}
//
//	@Override
//	protected void renderName(PrintWriter writer) {
//		RadioGroup group = getGroup();
//		if (group != null) {
//			writer.write(group.getId());
//		} else {
//			super.renderName(writer);
//		}
//	}

//	@Override
//	public void update() {
//		super.update();
//		if (getPage().getRequest().getParameterMap().containsKey(getId())) {
//			RadioGroup group = getGroup();
//			if (group != null) {
//				group.setValue(getValue());
//			}
//		}
//	}

	public RadioGroup getGroup() {
		Component parent = getParent();
		while (parent != null) {
			if (parent instanceof RadioGroup) {
				return (RadioGroup) parent;
			}

			parent = parent.getParent();
		}
		return null;
	}
}
