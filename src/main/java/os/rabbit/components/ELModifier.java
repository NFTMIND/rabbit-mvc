package os.rabbit.components;

import java.io.PrintWriter;

import org.apache.commons.beanutils.PropertyUtils;

import os.rabbit.IModifier;
import os.rabbit.parser.Range;

public class ELModifier implements IModifier {
	private Range range;
	private String expression;
	private ELComponent component;
	public ELModifier(ELComponent component, int s, int e, String propertyExpression) {
		range = new Range(s, e);
		this.component = component;
		this.expression = propertyExpression;
	
	}

	@Override
	public void render(PrintWriter writer) {
		Object value = component.getValue(expression);
		if(value != null) {
			writer.print(value);
			
		}
	}

	@Override
	public Range getRange() {
		return range;
	}
}
