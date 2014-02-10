package os.rabbit.examples;

import os.rabbit.components.DivAlone;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;

public class DivAloneExample extends SpringBeanSupportComponent {
	
	private AjaxButton btnShow;
	private AjaxButton btnClose;
	private DivAlone div;
	
	
	public DivAloneExample(Tag tag) {
		super(tag);
	}

	@Override
	protected void afterBuild() {
		super.afterBuild();
		
		
		
		btnShow.addButtonListener(new IButtonListener(){
			@Override
			public void click() {
				div.show();
				getPage().executeScript("$(\"#txtName\").focus();");
				
			}
		});
		btnClose.addButtonListener(new IButtonListener(){
			@Override
			public void click() {
				div.hide();
				
			}
		});
		
		
	}


	@Override
	protected void beforeRender() {
		super.beforeRender();


	}
	
	

}
