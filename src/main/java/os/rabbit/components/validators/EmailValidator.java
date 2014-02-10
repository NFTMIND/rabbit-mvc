package os.rabbit.components.validators;

import os.rabbit.components.Form;
import os.rabbit.components.form.FormComponent;
import os.rabbit.components.form.IValidator;

public class EmailValidator implements IValidator {
	private String name;
	
	public EmailValidator(String name) {
		this.name = name;
	}
	@Override
	public void validate(Form form, FormComponent<?> component) {
		Object obj = component.getValue();
		if(obj instanceof String) {
			String email = (String)obj;
			if(email.indexOf("@") == -1) {
				form.error(name + "格式不正確");
			}
		} else {
			form.error(name + "欄位型別必須是字串");
		}
	
		
	}

}
