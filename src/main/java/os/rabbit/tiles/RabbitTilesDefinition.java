package os.rabbit.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RabbitTilesDefinition {
	private HashMap<String, String> properties = new HashMap<String, String>();
	private String name;
	private String template;

	public String getName() {
		return name;
	}

	public String getAttributeValue(String name) {
		return properties.get(name);
	}

	public List<String> getAttributeNameList() {

		return new ArrayList<String>(properties.keySet());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public RabbitTilesDefinition(String name, String template) {
		this.name = name;
		this.template = template;
	}

	public void setAttribute(String name, String value) {
		properties.put(name, value);
	}
}
