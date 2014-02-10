package os.rabbit.components.form;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import os.rabbit.IRender;
import os.rabbit.components.ComponentAdapter;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class ComboBox extends FormComponent<String> {
	private LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();

	public ComboBox(Tag tag) {
		super(tag);

		new BodyModifier(this, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String value = getValue();
				Map<String, String> map = getOptions();
				
				for (String label : map.keySet()) {
					if(value != null && value.equals(map.get(label))) {
						writer.write("<option selected=\"selected\" value=\"" + map.get(label) + "\">" + label + "</option>");
					} else {
						writer.write("<option value=\"" + map.get(label) + "\">" + label + "</option>");
					}
				}
			}
		});
	}
	@Override
	protected void afterBuild() {
		getPage().addComponentListener(new ComponentAdapter() {
			
			@Override
			public void beforeRender() {
				for(String label : options.keySet()) {
					addOptions(label, options.get(label));
				}
			}
		
		});
	}


	public void addDefaultOption(String name, Object value) {
		options.put(name, value.toString());
	}

	public void addOptions(String name, Object value) {

		getOptions().put(name, value.toString());

	}

	public Map<String, String> getOptions() {
		Map<String, String> options = (Map<String, String>) getPage().getRequest().getAttribute(getId() + "_OPTIONS");
		if (options == null) {
			options = new LinkedHashMap<String, String>();
			getPage().getRequest().setAttribute(getId() + "_OPTIONS", options);
		}

		return options;
	}

	@Override
	protected String transform(Object value) {
		return (String)value;
	}

}
