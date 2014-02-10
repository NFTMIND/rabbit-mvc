package os.rabbit.components;

import java.io.PrintWriter;
import java.util.List;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.parser.Tag;

public class Tree<T> extends Component {

	private ITreeListener<T> listener;
	private ITreeDataProvider<T> dataProvider;
	private AjaxInvokeCallback callback;

	public Tree(Tag tag) {
		super(tag);
	
	}

	public void setRoot(T root) {
		setAttribute("ROOT", root);
	}

	public void setDataProvider(ITreeDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void setTreeListener(ITreeListener<T> listener) {
		this.listener = listener;
	}

	public T getRoot() {

		return (T) getAttribute("ROOT");
	}

	@Override
	protected void afterBuild() {
		callback = new AjaxInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {
				String id = getPage().getParameter("id");
				T node = dataProvider.getNode(id);
				repaintNode(node, true);
			}
		});
	

	}

	public void repaintNode(T node, Boolean hintExpand) {

		PrintWriter writer = new PrintWriter(getPage().getWriter());
		writer.write("<html id=\"" + dataProvider.getId(node) + "\">");
		writer.write("<![CDATA[");
		renderTree(writer, node, hintExpand);
		writer.write("]]>");
		writer.write("</html>");

	}

	private void renderTree(PrintWriter writer, T node, Boolean hintExpand) {
		String nodeId = dataProvider.getId(node);
		boolean expand = false;
		if(hintExpand == null) {
			expand = dataProvider.isExpand(node);
		} else {
			
			expand = hintExpand;
		}
		
		
		boolean hasChild = dataProvider.hasChildren(node);
		writer.print("<div id=\"" + nodeId + "\">");
		writer.print("<div style=\"width:20px;float:left\" onclick=\"");
		if(hasChild) {
			writer.print("if($('#"+nodeId + "_child').length == 0) {");
			callback.setCallbackParameter("id", nodeId);
			callback.render(writer);
			writer.print("} else {");
			writer.print("$('#"+nodeId + "_child').remove();");
			writer.print("$('#"+nodeId + "_op').attr('src', '"+getPage().getRequest().getContextPath()+"/rbt/expand.gif');");
			writer.print("}");
		}
		writer.print("\">");

		if (hasChild) {
			if (expand) {
				writer.print("<img id=\""+nodeId+"_op\" src=\""+getPage().getRequest().getContextPath()+"/rbt/fold.gif\" />");
			} else {
				writer.print("<img id=\""+nodeId+"_op\" src=\""+getPage().getRequest().getContextPath()+"/rbt/expand.gif\" />");
			}
		} else {
			writer.print("&nbsp;");
		}
		writer.print("</div>");
		writer.print("<div>");
		if (listener != null)
			listener.beforeNodeRender(writer, node);
		super.renderComponent(writer);
		if (listener != null)
			listener.afterNodeRender(writer, node);

		writer.println("</div>");
		if (hasChild && expand) {
			writer.print("<div style=\"margin-left:20px\" id=\"" + nodeId + "_child\">");

			writer.print("<div>");
			for (T child : dataProvider.getChildrens(node)) {
				renderTree(writer, child, null);
			}
			writer.print("</div>");

			writer.print("</div>");
		}
		writer.print("</div>");
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		writer.write("<div id=\"" + getId() + "\">");
		T root = getRoot();
		if (root == null) {
//			if (listener != null)
//				listener.emptyRender(writer);
			if(dataProvider.hasChildren(null)) {
				List<T> childrens = dataProvider.getChildrens(null);
				for(T node : childrens) {
					renderTree(writer, node, null);
				}
			}
		} else {
			renderTree(writer, root, null);

		}
		
		writer.write("</div>");
	}
}
