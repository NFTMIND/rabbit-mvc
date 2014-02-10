package os.rabbit.components.form;

import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class JSONBox extends FormComponent<Object> {

	public JSONBox(Tag tag) {
		super(tag);
	
		new BodyModifier(this, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				Object obj = getValue();
				if(obj instanceof JSONObject) {
					writer.print(((JSONObject)obj).toJSONString());
				} else if(obj instanceof JSONArray) {
					writer.print(((JSONArray)obj).toJSONString());
				}	
			
			}
		});
		new AttributeModifier(this,  "style", new IRender() {
			
			@Override
			public void render(PrintWriter writer) {
				writer.print("display:none");
			}
		});
	}

	@Override
	protected Object transform(Object value) {
		if(value == null) return null;
		return JSONValue.parse(value.toString());
	}

}
