package os.rabbit.components;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import os.rabbit.IModifier;
import os.rabbit.parser.Range;

public class ELComponent {
	private Range range;

	private String name;
	private Object value;
	private String expression;
	

	public ELComponent(int s, int e, String name, String expression) {
		range = new Range(s, e);
		this.name = name;
		this.expression = expression;
		
	}

	
	public Object getValue() {
		return value;
	}
	public void setValue(Object object) {
		this.value = object;
	}
	public String getName() {
		return name;
	}
	public String getExpression() {
		return expression;
	}
	public Range getRange() {
		return range;
	}


}
