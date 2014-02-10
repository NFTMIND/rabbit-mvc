package os.rabbit.components;

import java.io.PrintWriter;
import java.util.LinkedList;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class DownloadLink extends Component implements ITrigger {

	private String id;
	public DownloadLink(Tag tag) {
		super(tag);
		id = getId() + "$" + getModifiers().size();

		
	}
	
	@Override
	protected void afterBuild() {
		new AttributeModifier(this, "href", new IRender() {
			@Override
			public void render(PrintWriter writer) {
			
				writer.write(getPage().getRequestURI()+ "?rbtType=INVOKE&triggerId=" + id);
			}
		});
		
		getPage().addTrigger(id, this);
	
	}
	private LinkedList<IButtonListener> listeners = new LinkedList<IButtonListener>();


	public void addButtonListener(IButtonListener listener) {
		listeners.add(listener);
	}

	@Override
	public void invoke() {
		for (IButtonListener listener : listeners) {
			listener.click();
		}
	}
}
