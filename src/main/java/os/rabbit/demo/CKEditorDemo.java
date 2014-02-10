package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.form.CKEditor;
import os.rabbit.parser.Tag;

public class CKEditorDemo extends Component {

	private CKEditor editor;
	public CKEditorDemo(Tag tag) {
		super(tag);

	}
	
	@Override
	protected void afterBuild() {
		
	}
	
	@Override
	protected void beforeRender() {
		editor.setBroswerRoot(getPage().getRequest().getSession().getServletContext().getRealPath("/demo"));
		super.beforeRender();
	}
	
	

}
