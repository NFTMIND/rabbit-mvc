package os.rabbit.test;

import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.Include;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.Window;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;

public class IncludeTest extends SpringBeanSupportComponent{
	private AjaxButton btnLoad;
	private Window panelUserManager;
	private Include includeUserManager;
	private Component container;
	
	public IncludeTest(Tag tag) {
		super(tag);
	
	}
	@Override
	protected void afterBuild() {
		btnLoad.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
			
				includeUserManager.setURI("test.html");
				getPage().setAttribute("userManagerVisible", true);
				includeUserManager.setVisible(true);
				container.repaint();
				System.out.println("test");
			}
		});

	}
	
	@Override
	protected void beforeRender() {
		super.beforeRender();
		includeUserManager.setVisible(false);
		includeUserManager.setURI("test.html");
		
	}


}
