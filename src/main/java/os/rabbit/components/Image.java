package os.rabbit.components;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class Image extends Component {

	public Image(Tag tag) {
		super(tag);
		
		new AttributeModifier(this, "src", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String imageURL = getImageURL();
				if(imageURL != null) {
					writer.write(imageURL);
				}
			}
		});
	}
	
	

	public void setImageURL(String url) {
		getPage().getRequest().setAttribute(getId() + "_URL", url);
	}
	
	public String getImageURL() {
		return (String)getPage().getRequest().getAttribute(getId() + "_URL");
	}
	

}
