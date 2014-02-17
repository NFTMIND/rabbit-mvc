package os.rabbit.demo;

import os.rabbit.components.Label;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.ajax.EditableLabel;
import os.rabbit.components.ajax.IUpdateListener;
import os.rabbit.parser.Tag;

public class EditableLabelDemo extends SpringBeanSupportComponent {
	private Label label;
	private EditableLabel editableLabelComponent;
	public EditableLabelDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {
		editableLabelComponent.addUpdateListener(new IUpdateListener() {
			
			@Override
			public void update(String value) {
				label.setValue("您修改後的文字為：" + editableLabelComponent.getValue());
				label.repaint();
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		if(editableLabelComponent.getValue() == null) {
			editableLabelComponent.setValue("快點二下進行修改");
		}
	}

}
