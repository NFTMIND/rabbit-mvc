package os.rabbit.demo;

import java.util.ArrayList;
import java.util.List;

import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;

public class ChangeLabelDemo extends SpringBeanSupportComponent {
	private ListBuffer listObj;
	private AjaxButton btnObj;
	private Label lblText;

	public ChangeLabelDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		// 測試物件，正常從service取
		List<Obj> list = new ArrayList<Obj>();
		for (int i = 0; i < 10; i++) {
			list.add(new Obj(i, "name" + i));
		}

		// 給view值
		for (Obj obj : list) {
			btnObj.setCallbackParameter("arg", String.valueOf(obj.getId()));
			btnObj.setCallbackParameter("rid", String.valueOf(lblText.getRenderId()));
			
			btnObj.setTagAttribute("value", "按我" + obj.getId());
			lblText.setValue(obj.getName());
			listObj.flush();
		}

	}

	@Override
	protected void initial() {
		super.initial();

		btnObj.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				String rid = getPage().getParameter("rid");
				getPage().executeScript("$('[rid="+rid+"]').text('helloWorld')");
			}
		});

	
	}

	public class Obj {
		private int id;
		private String name;

		public Obj(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}