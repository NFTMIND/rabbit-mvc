package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class TagBodyModifier implements IModifier {
	private Range range;
	private IRender render;
	public TagBodyModifier(Tag tag, IRender render) {
		this.render = render;

		if(tag.hasBody()) {
			
			range = new Range(tag.getBodyStart(), tag.getBodyEnd());
		} else {
			
			range = new Range(tag.getEndTagRange().getStart(), tag.getEndTagRange().getEnd());
		}

	}
	public TagBodyModifier(Tag tag, final String replaceContent) {

		render = new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(replaceContent);
			}
		};
		if(tag.hasBody()) {
			
			range = new Range(tag.getBodyStart(), tag.getBodyEnd());
		} else {
			
			range = new Range(tag.getEndTagRange().getStart(), tag.getEndTagRange().getEnd());
		}

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
