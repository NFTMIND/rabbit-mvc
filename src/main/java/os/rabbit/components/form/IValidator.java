package os.rabbit.components.form;

import os.rabbit.components.Form;

public interface IValidator {
	public void validate(Form form, FormComponent<?> component);
}
