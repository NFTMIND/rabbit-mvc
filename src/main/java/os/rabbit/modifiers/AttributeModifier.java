package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.parser.Attribute;
import os.rabbit.parser.Range;

public class AttributeModifier implements IModifier {

	private IRender valueRender;
	private Range range;
	private Component component;
	private String attributeName;
	public AttributeModifier(final Component component, final String name) {
		this(component, name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String value = (String) component.getAttribute("ATTR_MODIFIER_VALUE$" + name);
				if (value != null) {
					writer.write(value);
				}
			}
		});

	}

	public void setValue(String value) {
		component.setAttribute("ATTR_MODIFIER_VALUE$" + attributeName, value);
	}

	public AttributeModifier(final Component component, final String name, final String value) {
		this(component, name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String localValue = (String) component.getAttribute("ATTR_MODIFIER_VALUE$" + name);
				if (localValue != null) {
					writer.write(localValue);
				} else {
					writer.write(value);
				}
			}
		});

	}

	public AttributeModifier(Component component, final String name, final IRender render) {
		this.attributeName = name;
		this.component = component;
		Attribute attr = component.getTag().getAttributes().get(name);
		if (attr != null) {
			range = new Range(attr.getStart(), attr.getEnd());
			valueRender = new IRender() {
				@Override
				public void render(PrintWriter writer) {
					renderAttribute(name, render, writer);
				}
			};
		} else {
			range = new Range(component.getTag().getNameEnd(), component.getTag().getNameEnd());
			valueRender = new IRender() {
				@Override
				public void render(PrintWriter writer) {
					renderAttribute(name, render, writer);
				}
			};
		}
		component.addModifier(this);
	}

	protected void renderAttribute(String name, IRender valueRender, PrintWriter writer) {
		writer.write(" ");
		writer.write(name);
		writer.write("=\"");
		valueRender.render(writer);
		writer.write("\"");
	}

	@Override
	public Range getRange() {
		return range;
	}



	
	@Override
	public void render(PrintWriter writer) {
		valueRender.render(writer);
	}
	
	@Override
	public String toString() {

		return component.getId() + " modifier";
	}

}
