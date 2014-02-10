package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class CheckBox extends FormComponent<String> {

	public CheckBox(Tag tag) {
		super(tag);

		
	}

	@Override
	protected void initial() {
		CheckBoxGroup group = getGroup();
		if (group != null)
			setName(group.getId());
		
		new AttributeModifier(this, "value", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				Object value = getValue();
				if (value != null) {

					writer.write(value.toString());
				}
			}

		});
		new AttributeModifier(this, "checked", "checked") {
			@Override
			protected void renderAttribute(String name, IRender valueRender, PrintWriter writer) {
				String value = getValue();
				if (value != null) {
					CheckBoxGroup group = getGroup();
					if (group != null) {
						if (group.contains(value)) {
							super.renderAttribute(name, valueRender, writer);
						}
					} else {
						if (getPage().getParameter(getId()) != null && getPage().getParameter(getId()).equals(getValue())) {
							super.renderAttribute(name, valueRender, writer);
						}
					}

				}
			};
		};
	}

	// @Override
	// protected void renderName(PrintWriter writer) {
	// CheckBoxGroup group = getGroup();
	// if (group != null) {
	// writer.write(group.getId());
	// } else {
	// super.renderName(writer);
	// }
	// }

	@Override
	public void update() {
		super.update();
	}

	@Override
	protected String transform(Object value) {
		return (String) value;
	}

	public CheckBoxGroup getGroup() {
		Component parent = getParent();
		while (parent != null) {
			if (parent instanceof CheckBoxGroup) {
				return (CheckBoxGroup) parent;
			}

			parent = parent.getParent();
		}
		return null;
	}

}
