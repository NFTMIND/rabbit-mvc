package os.rabbit.demo;
import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.parser.Tag;


public class ScriptEventCallbackDemo extends Component {
	private Label behaviorLabel;
	private Component mouseArea;
	private AjaxInvokeModifier mouseOverModifier;
	private AjaxInvokeModifier mouseOutModifier;
	public ScriptEventCallbackDemo(Tag tag) {
		super(tag);
	}
	@Override
	protected void initial() {
		mouseOverModifier = new AjaxInvokeModifier(mouseArea, "onmouseover", new ITrigger() {
			
			@Override
			public void invoke() {
				behaviorLabel.setValue("mouse over");
				/**
				 * repaint() method is used to tell broswer which component need to rerender.
				 * Note: repaint() method is only worked on AJAX invoke.
				 */
				behaviorLabel.repaint();
			}
		});
		mouseOutModifier = new AjaxInvokeModifier(mouseArea, "onmouseout", new ITrigger() {
			
			@Override
			public void invoke() {
				behaviorLabel.setValue("mouse out");
				/**
				 * repaint() method is used to tell broswer which component need to rerender.
				 * Note: repaint() method is only worked on AJAX invoke.
				 */
				behaviorLabel.repaint();
			}
		});
	}
}
