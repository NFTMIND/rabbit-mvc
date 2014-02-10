package os.rabbit.components.form;

import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public abstract class FormComponent<T> extends Component {
	private LinkedList<IValidator> validatorList = new LinkedList<IValidator>();

	public FormComponent(Tag tag) {
		super(tag);

		new AttributeModifier(this, "name", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				//renderName(writer);
				writer.write(getName());
			}
		});

	}
	
	private String name;
	public String getName() {
		if(name == null) return getId();
		
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
//
//	protected void renderName(PrintWriter writer) {
//		writer.write(getName());
//	}

	public void addValidator(IValidator validator) {
		validatorList.add(validator);
	}

	public static final int REQUEST = 0;
	public static final int SESSION = 1;
	private int storage = REQUEST;

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public void validate(Form form) {
		for (IValidator validator : validatorList) {
			validator.validate(form, this);
		}

	}

	public Form getForm() {
		Component running = this;
		while (running != null) {
			if (running instanceof Form) {

				return (Form) running;
			}
			running = running.getParent();
		}
		return null;
	}

	public void setValue(T object) {
		if (storage == REQUEST) {
			HttpServletRequest request = getPage().getRequest();
			request.setAttribute(getId(), object);
		} else if (storage == SESSION) {
			HttpServletRequest request = getPage().getRequest();
			HttpSession session = request.getSession();
			session.setAttribute(getId(), object);

		}
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		if (storage == REQUEST) {
			HttpServletRequest request = getPage().getRequest();
			return (T) request.getAttribute(getId());
		} else if (storage == SESSION) {
			HttpServletRequest request = getPage().getRequest();
			HttpSession session = request.getSession();
			return (T) session.getAttribute(getId());
		}
		return null;
	}
	
	

	public int getReadedIndex() {
		HttpServletRequest request = getPage().getRequest();
		Integer index = (Integer) request.getAttribute(getId() + "index");
		if (index == null) {
			return 0;
		} else {
			return index;
		}
	}

	public void setReadedIndex(int index) {
		HttpServletRequest request = getPage().getRequest();
		request.setAttribute(getId() + "index", index);
	}

	public boolean next() {
		int index = getReadedIndex();

		HttpServletRequest request = getPage().getRequest();
		String[] values = request.getParameterValues(getId());

		if (index >= values.length)
			return false;

		setValue(transform(values[index]));
		setReadedIndex(++index);

		return true;
	}

	protected abstract T transform(Object value);

	public void update() {
	
		String param = getPage().getParameter(getId());
	
		setValue(transform(param));
	}
}
