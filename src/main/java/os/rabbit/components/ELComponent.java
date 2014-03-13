package os.rabbit.components;

import org.apache.commons.beanutils.PropertyUtils;

import os.rabbit.parser.Range;

public class ELComponent {
	private Range range;

	private String name;
	private Object value;

	public ELComponent(int s, int e, String name) {
		range = new Range(s, e);
		this.name = name;

	}

	public Object getValue(String expression) {
		if (expression == null) {
			return value;
		}

		try {
			return (PropertyUtils.getProperty(value, expression));
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return null;
	}

	public void setValue(Object object) {
		this.value = object;
	}

	public String getName() {
		return name;
	}

	public Range getRange() {
		return range;
	}

}
