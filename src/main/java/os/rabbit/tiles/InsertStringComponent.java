package os.rabbit.tiles;

import java.io.PrintWriter;

import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class InsertStringComponent extends Component {

	public InsertStringComponent(Tag tag) {
		super(tag);

	}

	
	@Override
	public void renderComponent(final PrintWriter writer) {
	
		RabbitTilesDefinition definition = (RabbitTilesDefinition) getPage().getRequest().getAttribute(RabbitTilesServlet.RABBIT_TILES_DEFINITION);
	
		String value = definition.getAttributeValue(getTag().getAttribute("rabbit:id"));
		if(value != null)
			writer.write(value);
	}

}
