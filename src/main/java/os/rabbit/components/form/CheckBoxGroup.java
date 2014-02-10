package os.rabbit.components.form;

import java.util.LinkedList;
import java.util.List;

import os.rabbit.parser.Tag;

public class CheckBoxGroup extends FormComponent<List<String>> {

	public CheckBoxGroup(Tag tag) {
		super(tag);
	}

	@Override
	protected List<String> transform(Object value) {
		LinkedList<String> list = new LinkedList<String>();
		list.add((String) value);
		return list;
	}

	protected List<String> transform(String[] values) {
		LinkedList<String> list = new LinkedList<String>();
		for (String value : values) {
			list.add(value);
		}
		return list;
	}

	@Override
	public void update() {

		Object value = getPage().getParameterObject(getId());
	
		if (value instanceof String[]) {
			String[] params = (String[]) value;
			if (params != null) {
				System.out.println("group size;" + params.length);
				setValue(transform(params));
			}
		} else {
			String param = (String)value;
			if(param.length() == 0) return;
			setValue(transform(param));
		}

	}

	public boolean contains(String value) {
		List<String> values = getValue();
		return values != null && values.contains(value);

	}
}
