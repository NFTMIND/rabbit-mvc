package os.rabbit.components.form;

import java.io.PrintWriter;
import java.util.UUID;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.RenderInterruptedException;
import os.rabbit.callbacks.URLInvokeCallback;
import os.rabbit.components.CKEditorFileBroswer;
import os.rabbit.parser.Tag;

public class CKEditor extends TextBox {
	private URLInvokeCallback invoke;
	private String uuid = UUID.randomUUID().toString().replace("-", "");
	public CKEditor(Tag tag) {
		super(tag);
	}
	@Override
	protected void afterBuild() {
		getPage().addScriptImport("ckeditor", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRequest().getContextPath() + "/rbt/ckeditor/ckeditor.js");
		
			}
		});

		getPage().addScript("ckeditor_" + getId(), new IRender() {

			@Override
			public void render(PrintWriter writer) {
				writer.println("$(document).ready(function() {");
				writer.print("CKEDITOR.replace('" + getId() + "', {allowedContent:true,");
				writer.print("filebrowserBrowseUrl : '"+getPage().getRequest().getContextPath()+"/os/rabbit/components/file_broswer@.rbt',");
				//writer.print("filebrowserBrowseUrl : '"+getPage().getRequest().getContextPath()+"/file_broswer.rbt?id="+uuid+"',");
				
				//writer.print("filebrowserUploadUrl : '/uploader/upload.php?type=Files'");
				//writer.print("});");
				//writer.print("CKEDITOR.replace('" + getId() + "', ");

				//writer.print("{");
				//writer.print("filebrowserBrowseUrl: '");
				//invoke.render(writer);
				//writer.print("',");
				//writer.print("filebrowserUploadUrl: '/uploader/upload.php',");

				writer.println("toolbarGroups  : [");
				writer.println(" { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },");
				writer.println(" { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },");
				writer.println(" { name: 'links' },");
				writer.println(" { name: 'insert' },");
				// writer.println(" { name: 'forms' },");
				// writer.println(" { name: 'tools' },");
				writer.println(" { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },");
				writer.println(" { name: 'others' },");
				writer.println(" '/',");
				writer.println(" { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },");
				writer.println(" { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },");
				writer.println(" { name: 'styles' },");
				writer.println(" { name: 'colors' },");
				writer.println(" { name: 'about' }");

				writer.println("]");

				writer.println("});");
				writer.println("});");
			}
		});

		invoke = new URLInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {
				PrintWriter writer = new PrintWriter(getPage().getWriter());
				getPage().getResponse().setContentType("text/xml");
				writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?><Connector resourceType=\"Images\">");
				writer.println("<Error number=\"0\" />");
				writer.println("<CurrentFolder path=\"/Moon/\" url=\"/userfiles/images/Moon/\" acl=\"17\" />");
				writer.println("<Files>");
				writer.println("<File name=\"Moon1.jpg\" date=\"201212180045\" thumb=\"Moon1.jpg\" size=\"3\" />");
				writer.println("</Files>");
				writer.println("</Connector>");
				throw new RenderInterruptedException();
			}
		});

	}

	@Override
	protected String transform(Object value) {
		return (String) value;
	}

	@Override
	public void repaint() {

		String content = "";
		if (getValue() != null) {
			content = getValue().replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
		}
		executeScript("CKEDITOR.instances['" + getId() + "'].setData('" + content + "')");

	}
	@Override
	protected void renderValue(PrintWriter writer) {
		String value = getValue();
		if(value != null)
		writer.write(value.replace("&", "&amp;"));
	}

	public void setBroswerRoot(String root) {
		CKEditorFileBroswer.setRoot(uuid, root);
	}

	//
	// //
	// // @Override
	// protected void renderScript(PrintWriter writer) {
	// writer.println("if(CKEDITOR.instances['"+getId()+"'] != null) {");
	//
	// writer.println("	CKEDITOR.remove(CKEDITOR.instances['"+getId()+"']);");
	// writer.println("}");
	//
	// }

}
