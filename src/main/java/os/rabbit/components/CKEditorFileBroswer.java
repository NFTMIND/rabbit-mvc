package os.rabbit.components;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.components.form.IntBox;
import os.rabbit.components.form.TextBox;
import os.rabbit.components.form.UploadField;
import os.rabbit.parser.Tag;

public class CKEditorFileBroswer extends SpringBeanSupportComponent {
	private ComponentList<File> rowFile;
	private TextBox id;
	private TextBox parent;
	private Image imgType;
	private Label lblFileName;
	private Label lblFileSize;
	private Label lblLastModifiedTime;
	private IntBox funcNum;
	private ComponentList<File> rowPath;
	private Link link;
	private ContextMenuPopupMenu popupMenu;
	private Form form;
	private UploadField upload;
	private ScriptInvokeCallback deleteCallback;
	private ScriptInvokeCallback upzipCallback;

	private TextBox txtAddedFolder;
	private Button btnAddFolder;

	public CKEditorFileBroswer(Tag tag) {
		super(tag);
	}

	public String getRoot() {
		String root = rootMap.get(id.getValue());

		if (root == null) {

			return getPage().getRequest().getSession().getServletContext().getRealPath("");
		}

		return root;
	}

	private void upzip(File source, String targetPath) {

		try {
		
			ZipFile zipFile = new ZipFile(source);
			Enumeration emu = zipFile.entries();
			int i = 0;
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();

				if (entry.isDirectory()) {
					new File(targetPath + "/" + entry.getName()).mkdirs();
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
				File file = new File(targetPath + "/" + entry.getName());
				System.out.println(entry.getName());
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

				int count;
				byte data[] = new byte[1024];
				while ((count = bis.read(data, 0, 1024)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void afterBuild() {
		btnAddFolder.addUpdateComponent(funcNum);
		btnAddFolder.addUpdateComponent(parent);
		btnAddFolder.addUpdateComponent(id);
		btnAddFolder.addUpdateComponent(txtAddedFolder);
		btnAddFolder.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				String path = getRoot() + (parent.getValue() == null ? "" : parent.getValue());

				File f = new File(path + "/" + txtAddedFolder.getValue());
				f.mkdirs();

			}
		});
		upzipCallback = new ScriptInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {

				String path = getRoot() + getPage().getParameter("path");
				System.out.println("delete fiile:" + getPage().getParameter("path"));
				File f = new File(path);
				if (f.exists()) {
					upzip(f, f.getParent());
				}
			}
		});

		deleteCallback = new ScriptInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {

				String path = getRoot() + getPage().getParameter("path");
				System.out.println("delete fiile:" + getPage().getParameter("path"));
				File f = new File(path);
				if (f.exists()) {
					delete(f);

				}
			}

			private void delete(File file) {
				if (file.isDirectory()) {
					for (File f : file.listFiles()) {
						delete(f);
					}

				}
				file.delete();
			}
		});
		deleteCallback.setConfirm("您是否確定要刪除？");
		upzipCallback.addUpdateComponent(funcNum);
		upzipCallback.addUpdateComponent(parent);
		upzipCallback.addUpdateComponent(id);

		deleteCallback.addUpdateComponent(funcNum);
		deleteCallback.addUpdateComponent(parent);
		deleteCallback.addUpdateComponent(id);
		form.addFormListener(new IFormListener() {

			@Override
			public void submit() {

				// String path =
				// getPage().getRequest().getSession().getServletContext().getRealPath(parent.getValue()
				// == null ? "" : parent.getValue());
				String path = getRoot() + (parent.getValue() == null ? "" : parent.getValue());
				File f = new File(path + "/" + upload.getValue().getFileName());
				try {
					FileOutputStream out = new FileOutputStream(f);
					out.write(upload.getValue().getData());
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		link.setTagBodyModifier(true);
		//rowFile.setTagAttributeModifier("onclick", true);
		rowPath.setListListener(new IListListener<File>() {

			@Override
			public void emptyRender(PrintWriter writer) {
			}

			@Override
			public void beforeEachRender(PrintWriter writer, File object, int index) {

				int l = getRoot().length();

				String path = object.getAbsolutePath().substring(l, object.getAbsolutePath().length());
				link.setRedirectURL("?parent=" + path + "&funcNum=" + funcNum.getValue() + "&id=" + id.getValue());
				link.setTagBody("/" + object.getName());

			}

			@Override
			public void afterEachRender(PrintWriter writer, File object, int index) {
			}
		});
		rowFile.setListListener(new IListListener<File>() {

			@Override
			public void emptyRender(PrintWriter writer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterEachRender(PrintWriter writer, File object, int index) {
				// if (index != 0 && index % 3 == 0) {
				// writer.println("</tr><tr>");
				// }
			}

			@Override
			public void beforeEachRender(PrintWriter writer, File object, int index) {

				String rootRealPath = getRoot();

				String realPath = object.getAbsolutePath();
				String path = realPath.substring(rootRealPath.length(), realPath.length());
				path = path.replace("\\", "/");

				if (object.isDirectory()) {
					imgType.setImageURL(getPage().getRequest().getContextPath() + "/rbt/ckeditor/folder.png");
				} else if (object.getName().endsWith(".png") || object.getName().endsWith(".jpg") || object.getName().endsWith(".gif")) {
					imgType.setImageURL(getPage().getRequest().getContextPath() + "/rbt/ckeditor/picture.png");
				} else if (object.getName().endsWith("zip")) {

					imgType.setImageURL(getPage().getRequest().getContextPath() + "/rbt/ckeditor/page_white_zip.png");

				} else {
					imgType.setImageURL(getPage().getRequest().getContextPath() + "/rbt/ckeditor/page_white_text.png");

				}

				lblFileName.setValue(object.getName());
				if (object.isDirectory()) {
					rowFile.setTagAttribute("onclick", "location.href='?id=" + id.getValue() + "&funcNum=" + funcNum.getValue() + "&parent=/" + path + "'");
				} else {
					rowFile.setTagAttribute("onclick", "select('" + getPage().getRequest().getContextPath() + path + "')");
				}
				String size = "";
				long value = object.length() / 1024;
				if(value <= 0) {
					size = object.length() + " Byte";
				} else {
					size = value + " KB";
				}
				lblFileSize.setValue(size);
				

				lblLastModifiedTime.setValue(dateFomrat.format(new Date(object.lastModified())));

				deleteCallback.setCallbackParameter("path", path);
				upzipCallback.setCallbackParameter("path", path);
				popupMenu.clearItem();
				popupMenu.addItem("刪除", deleteCallback);

				if (object.getName().endsWith(".zip")) {
					popupMenu.addItem("解壓縮", upzipCallback);

				}
			}

		});
	}

	private SimpleDateFormat dateFomrat = new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss");

	@Override
	protected void beforeRender() {

		String parentPath = getPage().getParameter("parent");
		if (parentPath == null) {
			parentPath = "/";
		}
		String realPath = getRoot() + parentPath;

		if (funcNum.getValue() == null)
			funcNum.setValue(getPage().getParameterAsInteger("CKEditorFuncNum"));

		LinkedList<File> list = new LinkedList<File>();
		File parent = new File(realPath);
		for (File f : parent.listFiles()) {
			list.add(f);
		}
		Collections.sort(list, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isDirectory()) {

					return o1.getName().compareTo(o1.getName());
				} else if (o1.isDirectory() && !o2.isDirectory()) {
					return -1;
				} else if (!o1.isDirectory() && o2.isDirectory()) {
					return 1;
				}
				return 0;
			}
		});

		LinkedList<File> paths = new LinkedList<File>();
		File curr = parent;
		while (true) {
			paths.addFirst(curr);

			if (curr.getAbsolutePath().equals(getRoot()))
				break;
			curr = curr.getParentFile();
		}

		rowPath.setCollection(paths);
		rowFile.setCollection(list);
	}

	private static HashMap<String, String> rootMap = new HashMap<String, String>();

	public static void setRoot(String uuid, String root) {
		rootMap.put(uuid, root);

	}

}