package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.parser.Attribute;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class TagAttributeModifier implements IModifier {

	private IRender valueRender;
	private Range range;
	public TagAttributeModifier(Tag tag, final String name, final String value) {
		this(tag, name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(value);
			}
		});
		
	}
	public TagAttributeModifier(Tag tag, final String name, final IRender render) {
		Attribute attr = tag.getAttributes().get(name);
		if(attr != null) {
			range = new Range(attr.getValueStart(), attr.getValueEnd());
			valueRender = new IRender() {
				@Override
				public void render(PrintWriter writer) {
					render.render(writer);
				}
			};
		} else {
			range = new Range(tag.getNameEnd(), tag.getNameEnd());
			valueRender = new IRender() {
				@Override
				public void render(PrintWriter writer) {
					writer.write(" ");
					writer.write(name);
					writer.write("=\"");
					render.render(writer);
					writer.write("\"");
				}
			};
		}
	}
	
	@Override
	public Range getRange() {
		return range;
	}
	@Override
	public void render(PrintWriter writer) {
		valueRender.render(writer);
	}
}
