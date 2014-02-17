package os.rabbit.demo;

import java.util.Calendar;

import os.rabbit.ITrigger;
import os.rabbit.components.Label;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.parser.Tag;


public class TriggerInvokeDemo extends SpringBeanSupportComponent {
	private Label lblCurrentTime;
	public TriggerInvokeDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		
		getPage().addTrigger("inovkeMe", new ITrigger(){

			@Override
			public void invoke() {
				lblCurrentTime.setValue(Calendar.getInstance().getTimeInMillis());
				lblCurrentTime.repaint();
			}
			
		});

		
	}

}