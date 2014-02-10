package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class BodyModifier implements IModifier {
	private Range range;
	private IRender render;
	public BodyModifier(Component component, IRender render) {
		this.render = render;
		Tag tag = component.getTag();
		if(tag.hasBody()) {
			
			range = new Range(tag.getBodyStart(), tag.getBodyEnd());
		} else {
			
			range = new Range(tag.getEndTagRange().getStart(), tag.getEndTagRange().getEnd());
		}
		component.addModifier(this);
	}
	public BodyModifier(Component component, final String replaceContent) {

		render = new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(replaceContent);
			}
		};
		Tag tag = component.getTag();
		if(tag.hasBody()) {
			
			range = new Range(tag.getBodyStart(), tag.getBodyEnd());
		} else {
			
			range = new Range(tag.getEndTagRange().getStart(), tag.getEndTagRange().getEnd());
		}
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
