package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class TagNameModifier implements IModifier {
	private Range range;
	private IRender render;
	public TagNameModifier(Component component, IRender render) {
		this.render = render;
		Tag tag = component.getTag();
		
		range = new Range(tag.getNameStart(), tag.getNameEnd());
		
		component.addModifier(this);
		
	}

	
	@Override
	public Range getRange() {
		return range;
	}
	
	@Override
	public void render(PrintWriter writer) {
		render.render(writer);
	}
	
	
}
