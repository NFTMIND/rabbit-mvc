package os.rabbit.modifiers;

import java.io.PrintWriter;

import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.parser.Attribute;
import os.rabbit.parser.Range;

public class RemoveAttributeModifier implements IModifier {

	private IRender valueRender;
	private Range range;

	public RemoveAttributeModifier(Component component, final String name) {
		Attribute attr = component.getTag().getAttributes().get(name);
		if(attr != null) {
			range = new Range(attr.getStart(), attr.getEnd());
			valueRender = new IRender() {
				@Override
				public void render(PrintWriter writer) {
					
				}
			};
			component.addModifier(this);
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
