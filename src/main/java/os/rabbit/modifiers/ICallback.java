package os.rabbit.modifiers;

import os.rabbit.IRender;

public interface ICallback extends IRender {

	String getId();

	void setURI(String uri);

	void setCallbackParameter(String key, String value);

	void setCallbackParameter(String key, String[] values);

	void removeCallbackParameter(String key);

}
