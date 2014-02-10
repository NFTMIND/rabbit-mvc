package os.rabbit.demo;

import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import os.rabbit.components.Component;
import os.rabbit.components.ComponentAdapter;
import os.rabbit.components.ComponentList;
import os.rabbit.components.Form;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IFormListener;
import os.rabbit.components.IListListener;
import os.rabbit.components.Label;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.FileItem;
import os.rabbit.components.form.FileUpload;
import os.rabbit.components.form.IFileUploadListener;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class FileUploadDemo extends Component {
	private Form form;
	private TextBox uploadFiles;
	private FileUpload container;
	private Component files;
	private ComponentList<JSONObject> row;
	private Label lblId;
	private Label lblName;
	private Label lblSize;
	private AjaxButton btnDel;

	public FileUploadDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void afterBuild() {
		btnDel.setLoadingScreen(true);
		btnDel.addUpdateComponent(uploadFiles);
		btnDel.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				JSONArray array = null;
				if (uploadFiles.getValue() != null) {
					array = (JSONArray) JSONValue.parse(uploadFiles.getValue());

				}
				if (array != null) {
					String id = getPage().getRequest().getParameter("id");
					for (Object obj : array) {
						JSONObject o = (JSONObject) obj;
						if (o.get("id").equals(id)) {
							array.remove(o);
							break;
						}
					}
				
					uploadFiles.setValue(array.toJSONString());
				}
				files.repaint();
			}
		});

		container.addFileUploadListener(new IFileUploadListener() {
			@Override
			public void upload(FileItem item) {
				JSONArray array = null;
				if (uploadFiles.getValue() != null) {
					array = (JSONArray) JSONValue.parse(uploadFiles.getValue());
				} else {
					array = new JSONArray();
				}
				JSONObject obj = new JSONObject();
				obj.put("fileName", item.getName());
				obj.put("id", item.getId());
				obj.put("path", getPage().getRequest().getContextPath() + "/temp/" + item.getId());
				array.add(obj);
	
				uploadFiles.setValue(array.toJSONString());

				files.repaint();
			}
		});
		container.addUpdateComponent(uploadFiles);

		form.addFormListener(new IFormListener() {

			@Override
			public void submit() {
				
			}
		});

		row.setListListener(new IListListener<JSONObject>() {

			@Override
			public void emptyRender(PrintWriter writer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeEachRender(PrintWriter writer, JSONObject object, int index) {
				btnDel.setCallbackParameter("id", object.get("id").toString());
				lblId.setValue(object.get("id"));
				lblName.setValue(object.get("fileName"));
				lblSize.setValue(object.get("path"));

			}

			@Override
			public void afterEachRender(PrintWriter writer, JSONObject object, int index) {
				// TODO Auto-generated method stub

			}
		});
		files.addComponentListener(new ComponentAdapter() {

			@Override
			public void beforeRender() {
				JSONArray array = null;
				if (uploadFiles.getValue() != null) {
					array = (JSONArray) JSONValue.parse(uploadFiles.getValue());

				}

				row.setCollection(array);
			}

		
		});
	}

	@Override
	protected void beforeRender() {

	}

}
