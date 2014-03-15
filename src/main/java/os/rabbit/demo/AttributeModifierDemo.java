package os.rabbit.demo;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class AttributeModifierDemo extends Component {
	private Label label;
	private Button changeColor;
	public AttributeModifierDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {
		new AttributeModifier(this, "style", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String color = (String)getAttribute("color");
				if(color != null)
					writer.write("color:" + color);
			}
		});
		
		changeColor.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				setAttribute("color", "#FF0000");
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		label.setValue("Demo text");
	}

}
