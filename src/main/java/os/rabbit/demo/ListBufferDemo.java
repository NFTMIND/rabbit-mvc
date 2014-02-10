package os.rabbit.demo;

import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.UpdateComponent;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class ListBufferDemo extends SpringBeanSupportComponent {
	@UpdateComponent("txt")
	private AjaxButton btn;
	private TextBox txt;
	private ListBuffer listBuffer;
	private Label labelA;
	
	private ListBuffer nestListBuffer;
	private Label labelB;
	public ListBufferDemo(Tag tag) {
		super(tag);
	
	}

	@Override
	protected void afterBuild() {
		btn.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				System.out.println(txt.getValue());
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		for(int loop = 0; loop < 100; loop++) {
			labelA.setValue(loop);
			for(int loop1 = 0; loop1 < Math.random()*5-1; loop1++) {
				labelB.setValue(loop1);
				nestListBuffer.flush();
			}
			listBuffer.flush();
		}
	}
}
