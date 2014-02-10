package os.rabbit.components;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class Label extends Component {
	private boolean multiline = true;
	private boolean html;

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public Label(Tag tag) {
		super(tag);
		
		new BodyModifier(this, new IRender() {
			
			@Override
			public void render(PrintWriter writer) {
				Object value = getValue();
				if(value != null) {
					String strVal = value.toString();
					if(!html) {
						strVal = strVal.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
					}
					if(multiline) {
	
						strVal = strVal.replace("\n", "<br />");
					}
					
				
					writer.write(strVal);
				}
			}
		});

	}

	public void setMultiline(boolean multiline) {
		this.multiline = multiline;
	}

	public Object getValue() {
		return getAttribute("value");
	}

	public void setValue(Object value) {
	
		setAttribute("value", value);
	}

}
