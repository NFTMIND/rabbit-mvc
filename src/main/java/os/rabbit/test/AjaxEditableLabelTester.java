package os.rabbit.test;

import java.io.PrintWriter;
import java.util.LinkedList;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.ComponentList;
import os.rabbit.components.IListListener;
import os.rabbit.components.ajax.EditableLabel;
import os.rabbit.components.ajax.IUpdateListener;
import os.rabbit.components.form.BooleanCheckBox;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.parser.Tag;

public class AjaxEditableLabelTester extends Component {

	
	public AjaxEditableLabelTester(Tag tag) {
		super(tag);
		// TODO Auto-generated constructor stub
	}


	private ComponentList<String> list;
	
	private EditableLabel label;
	private BooleanCheckBox checkBox;
	
	
	@Override
	protected void afterBuild() {
		AjaxInvokeModifier modifier = new AjaxInvokeModifier(checkBox, "onclick", new ITrigger() {
			
			@Override
			public void invoke() {
			
			}
		});
		modifier.addUpdateComponent(checkBox);
		
		label.setEmptyString("點此修改");
		list.setListListener(new IListListener<String>() {
			
			@Override
			public void emptyRender(PrintWriter writer) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeEachRender(PrintWriter writer, String object, int index) {
				label.setValue(object);
			}
			
			@Override
			public void afterEachRender(PrintWriter writer, String object, int index) {
				// TODO Auto-generated method stub
				
			}
		});
		label.addUpdateListener(new IUpdateListener() {
			
			@Override
			public void update(String value) {
			
			}
		});
	}
	@Override
	protected void beforeRender() {
	
		LinkedList<String> datas = new LinkedList<String>();
		
		datas.add("test1");
		datas.add("test2");
		datas.add("test3");
		list.setCollection(datas);
	}
}
