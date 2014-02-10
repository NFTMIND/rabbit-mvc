package os.rabbit.modifiers;

import java.util.Map;

public class JSProxy {

	private StringBuffer buffer = new StringBuffer();

	public void invoke(String uri, String triggerId, Map<String, String> parameters, boolean loadingScreen) {
		buffer.append("Rabbit.invoke('");
		buffer.append(uri);
		buffer.append("','");
		buffer.append(triggerId);
		buffer.append("',");
		StringBuffer parameterBuffer = new StringBuffer();
		for (String key : parameters.keySet()) {
			parameterBuffer.append("'");
			parameterBuffer.append(key);
			parameterBuffer.append("':");
			parameterBuffer.append("'");
			parameterBuffer.append(parameters.get(key));
			parameterBuffer.append("'");
			parameterBuffer.append(",");
		}

		if (parameterBuffer.length() > 0) {
			parameterBuffer.deleteCharAt(buffer.length() - 1);
			parameterBuffer.insert(0, "{");
			parameterBuffer.append("}");
		} else {
			parameterBuffer.append("null");
		}
		buffer.append(parameterBuffer.toString());
		buffer.append(", " + loadingScreen + ");");
	}

	@Override
	public String toString() {
		return buffer.toString();
	}

}
