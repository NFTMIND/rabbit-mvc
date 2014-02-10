package os.rabbit.components.form;

import os.rabbit.parser.Tag;


public class RadioGroup extends FormComponent<String> {

	public RadioGroup(Tag tag) {
		super(tag);
	}

	@Override
	protected String transform(Object value) {
		return (String)value;
	}

}
