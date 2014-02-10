package os.rabbit.components.form;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import os.rabbit.Help;
import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.UploadFile;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.callbacks.InvokeType;
import os.rabbit.callbacks.URLInvokeCallback;
import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class FileUpload extends FormComponent<String> {
	private URLInvokeCallback callback;
	private AjaxInvokeCallback uploadComplete;
	private LinkedList<IFileUploadListener> listeners = new LinkedList<IFileUploadListener>();
	private String types = "*";
	private String typesDescription = "全部檔案";
	private String uploadButtonName = "選擇檔案";

	public FileUpload(Tag tag) {
		super(tag);
	}

	@Override
	protected void afterBuild() {
		uploadComplete = new AjaxInvokeCallback(getPage(), new ITrigger() {
			@Override
			public void invoke() {
				String fileName = getPage().getParameter("fileName");

				String path = getPage().getRequest().getSession().getServletContext().getRealPath("/temp/" + getValue());

				fire(new FileItem(getValue(), fileName, new File(path)));
			}
		});

		callback = new URLInvokeCallback(InvokeType.INVOKE_WITHOUT_PAGE_RENDER, getPage(), new ITrigger() {
			@Override
			public void invoke() {
				UploadFile object = (UploadFile) getPage().getParameterObject(getId() + "File");

				File tempFolder = new File(getPage().getRequest().getSession().getServletContext().getRealPath("/temp"));
				if (!tempFolder.exists()) {
					tempFolder.mkdirs();
				}

				String uuid = UUID.randomUUID().toString().replace("-", "");
				File file = new File(tempFolder, uuid);

				try {
					FileOutputStream out = new FileOutputStream(file);
					IOUtils.write(object.getData(), out);
					out.close();

					getPage().getWriter().write("{\"id\":\"" + getId() + "\", \"uuid\":\"" + uuid + "\", \"imagePath\":\"" + getPage().getRequest().getContextPath() + "/temp/" + uuid + "\"}");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		getPage().addCSSImport("fileUpload-CSS", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/uploadify/uploadify.css");
			}
		});
		getPage().addScriptImport("fileUpload-ScriptImport", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/uploadify/jquery.uploadify.min.js");
			}
		});
		getPage().addScriptImport("JSON-Lib", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/json2.js");
			}
		});
		getPage().addScript(getId() + "$fileUpload-Script", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.println("$(document).ready(function() {");
				writer.println("	$('#" + getId() + "_component').uploadify({");

				writer.println("'fileObjName' : '" + getId() + "File',");
				writer.println("		'swf':'" + getPage().getRelativelyRoot() + "/rbt/uploadify/uploadify.swf',");
				writer.print("		'uploader':'");
				callback.setURI(getPage().getRequestURI());
				callback.render(writer);
				writer.println("',");
				writer.println("		'cancelImg':'" + getPage().getRelativelyRoot() + "/rbt/uploadify/uploadify-cancel.png',");
				writer.println("		'buttonText': '" + getUploadButtonName() + "',");
				writer.println("		'auto': true,");
				writer.println("		'onUploadSuccess': onUploadSuccess,");
				writer.println("		'multi': false,");
				writer.println("		'fileTypeDesc' : '" + getTypesDescription() + "',");
				writer.println("		'fileTypeExts' : '" + getTypes() + "'");
				writer.println("	});");
				writer.println("});");
			}
		});

		getPage().addScript("fileUpload-Script", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				// 如果上傳成功
				writer.println("function onUploadSuccess(file, data, response) {");

				//writer.println("	alert(data);");

				writer.println("	var serverData = JSON.parse(data);");

				writer.println("	$('#' + serverData.id).val(serverData.uuid);");
				// writer.println("	$('#' + serverData.id + '_image').attr('src', serverData.imagePath);");
				uploadComplete.setCallbackParameter(getId(), "'+$('#" + getId() + "').fieldValue()+'");
				uploadComplete.setCallbackParameter("fileId", "'+serverData.uuid+'");
				uploadComplete.setCallbackParameter("fileName", "'+file.name+'");
				uploadComplete.render(writer);

				writer.println("}");

			}
		});

		Component container = getContainer();
		if (container != null) {
			final Method method = Help.getMethod(container.getClass(), getId() + "$onUpload", FileItem.class);
			if(method != null) {
				addFileUploadListener(new IFileUploadListener() {
					
					@Override
					public void upload(FileItem item) {
						try {
							method.invoke(FileUpload.this);
						} catch (Exception e) {
							e.printStackTrace();
						
						}
					}
				});
			}
		}
	}

	public void addFileUploadListener(IFileUploadListener listener) {
		listeners.add(listener);
	}

	private void fire(FileItem item) {
		for (IFileUploadListener l : listeners) {
			l.upload(item);
		}
	}

	@Override
	protected String transform(Object value) {
		if (value == null)
			return null;

		return value.toString();
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTypesDescription() {
		return typesDescription;
	}

	public void setTypesDescription(String typesDescription) {
		this.typesDescription = typesDescription;
	}

	public void setUploadButtonName(String name) {
		uploadButtonName = name;
	}

	public String getUploadButtonName() {
		return uploadButtonName;
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		// JsonGeneratorFactory generatorFactory =
		// JsonGeneratorFactory.getInstance();
		// JSONGenerator generator = generatorFactory.newJsonGenerator();
		// writer.write("<img width=\"50\" id=\"" + getId() + "_image\" src=\""
		// + getPage().getRequest().getContextPath() + "/temp/" + getValue() +
		// "\" />");

		writer.write("<input type=\"hidden\" name=\"" + getId() + "\" id=\"" + getId() + "\" value=\"" + getValue() + "\" />");
		writer.write("<input type=\"hidden\" name=\"" + getId() + "_component\" id=\"" + getId() + "_component\" value=\"" + getValue() + "\" />");

	}

	// public void repaintImage() {
	// PrintWriter writer = new PrintWriter(getPage().getWriter());
	// writer.write("<html id=\"" + getId() +"_image" + "\">");
	// writer.write("<![CDATA[");
	// writer.write("<img width=\"50\" id=\""+getId()+"_image\" src=\""+getPage().getRequest().getContextPath()+"/temp/"+
	// getValue() +"\" />");
	// writer.write("]]>");
	// writer.write("</html>");
	// writer.write("<script>");
	// writer.write("<![CDATA[");
	// renderScript(writer);
	// writer.write("]]>");
	// writer.write("</script>");
	// }

	// @Override
	// public void repaint() {
	// PrintWriter writer = new PrintWriter(getPage().getWriter());
	// writer.write("<html id=\"" + getId() + "\">");
	// writer.write("<![CDATA[");
	// writer.write("<input type=\"hidden\" name=\"" + getId() + "\" id=\"" +
	// getId() + "\" value=\"" + getValue() + "\" />");
	//
	// writer.write("]]>");
	// writer.write("</html>");
	//
	// writer.write("<script>");
	// writer.write("<![CDATA[");
	// renderScript(writer);
	// writer.write("$('#" + getId() + "_image').attr('src', '" +
	// getPage().getRequest().getContextPath() + "/temp/" + getValue() + "');");
	// writer.write("]]>");
	// writer.write("</script>");
	//
	// }

	public File getFile() {

		String path = getPage().getRequest().getSession().getServletContext().getRealPath("/temp/" + getValue());
		return new File(path);
	}

	public void addUpdateComponent(Component cmp) {
		uploadComplete.addUpdateComponent(cmp);
	}

	public void setCallbackParameter(String key, String value) {
		uploadComplete.setCallbackParameter(key, value);
	}

	public void setCallbackParameter(String key, String[] value) {
		uploadComplete.setCallbackParameter(key, value);
	}

	public void removeCallbackParameter(String key) {
		uploadComplete.removeCallbackParameter(key);
	}

}
