package os.rabbit.components.validators;

import os.rabbit.components.Form;
import os.rabbit.components.form.FormComponent;
import os.rabbit.components.form.IValidator;

public class RequiredValidator implements IValidator {
	private String name;
	
	public RequiredValidator(String name) {
		this.name = name;
	}
	@Override
	public void validate(Form form, FormComponent<?> component) {
		Object obj = component.getValue();
		if(obj instanceof String) {
			String value = (String)obj;
			if(value.trim().length() > 0) {
				return;
			}
		}
		if(obj != null) {
			return;
		}
		if(name == null) {
			name = component.getId();
		}
		form.error(name + "為必填欄位");
	}

}
