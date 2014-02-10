package os.rabbit.components;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class Link extends Component {

	public Link(Tag tag) {
		super(tag);

		if (tag.getName().equals("a")) {
			new AttributeModifier(this, "href", new IRender() {
				@Override
				public void render(PrintWriter writer) {
					String imageURL = getRedirectURL();
					if (imageURL != null) {
						writer.write(imageURL);
					}
				}
			});
		} else if (tag.getName().equals("input")) {
			new AttributeModifier(this, "onclick", new IRender() {
				@Override
				public void render(PrintWriter writer) {
					String imageURL = getRedirectURL();
					if (imageURL != null) {
						writer.write("location.href='" + imageURL + "';");
					}
				}
			});
		}
	}

	public void setRedirectURL(String url) {
		setAttribute("URL", url);
	}

	public String getRedirectURL() {
		return (String) getAttribute("URL");
	}

}
