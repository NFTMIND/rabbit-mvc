package os.rabbit.demo;

import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.UpdateComponent;
import os.rabbit.parser.Tag;

public class ListBufferDemo extends SpringBeanSupportComponent {
	@UpdateComponent("txt")
	private ListBuffer listBuffer;
	private Label lblNumber;
	

	public ListBufferDemo(Tag tag) {
		super(tag);
	
	}
	
	@Override
	protected void initial() {
		listBuffer.setEmptyDataMessage("目前尚無資料");
	}

	
	@Override
	protected void beforeRender() {
//		for(int loop = 0; loop < 100; loop++) {
//			lblNumber.setValue(loop);
//			listBuffer.flush();
//		}
	}
}
