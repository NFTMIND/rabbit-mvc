package os.rabbit.demo;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class TriggerDemo extends Component {

	public TriggerDemo(Tag tag) {
		super(tag);
	}

	@Override
	// note: register Trigger need to write it on initial() method. because registering Trigger can be only once.
	public void initial() {
		getPage().addTrigger("triggerId", new ITrigger() {

			public void invoke() {
				// this area program will be executed.
			}
		});
	}

}
