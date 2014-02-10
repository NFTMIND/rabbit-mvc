package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.ComponentAdapter;
import os.rabbit.components.IComponentVisitor;
import os.rabbit.parser.Tag;

public class MetaDemo extends Component {
	public MetaDemo(Tag tag) {
		super(tag);
	}


	private Component metaCmp;
	
	
	@Override
	protected void afterBuild() {
		getPage().addComponentListener(new ComponentAdapter() {
			
			@Override
			public void beforeRender() {
				metaCmp.setTagAttribute("value", "hello");
			}
			
		
		});
		getPage().
		getPage().visit(new IComponentVisitor() {
			
			@Override
			public boolean visit(Component component) {
				if(component.getId().equals("myMetaRabbitId")) {
					//component.setTagAttributeModifier("value", true);
					metaCmp = component;
					
					
					return false;
				}
				return true;
			}
		});
	}
	

}
