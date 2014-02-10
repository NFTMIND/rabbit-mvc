package os.rabbit.components;

import java.io.PrintWriter;
import java.util.List;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.components.form.FormComponent;
import os.rabbit.parser.Tag;

public class Paging extends FormComponent<String> {
	private ScriptInvokeCallback callback;
	public Paging(Tag tag) {
		super(tag);
		

	}
	@Override
	protected void afterBuild() {
		ITrigger trigger = new ITrigger() {

			@Override
			public void invoke() {
				if (getValue() == null) {
					setValue("0/0");
				}
				String[] data = getValue().split("/");
				int code = Integer.parseInt(data[0]);
				int size = Integer.parseInt(data[1]);
				setPageSize(size);
				setPageCode(code);
			}
		};
		callback = new ScriptInvokeCallback(getPage(), trigger);
		callback.addUpdateComponent(this);
	}

	public void setTotal(long total) {
		setAttribute("total", total);
	}

	public long getTotal() {
		Long total = (Long) getAttribute("total");
		if (total == null)
			total = 0L;

		return total;
	}

	public int getPageSize() {
		Integer size = (Integer) getAttribute("size");
		if (size == null) {
			size = defaultPageSize;
		}

		return size;
	}
	
	private int defaultPageSize = 10;
	public void setDefaultPageSize(int size) {
		this.defaultPageSize = size;
		
	}

	public void setPageSize(int size) {
		setAttribute("size", size);

		setValue(getPageCode() + "/" + getPageSize());
	}

	public int getPageCode() {
		Integer pageCode = (Integer) getAttribute("pageCode");
		if (pageCode == null)
			pageCode = 0;

		return pageCode;
	}

	public void setPageCode(int pageCode) {
		setAttribute("pageCode", pageCode);

		setValue(getPageCode() + "/" + getPageSize());
	}
	public int getPageTotal() {
		
		return (int)(getPageSize()/getTotal());
	}
	@Override
	protected void beforeRender() {
		// TODO Auto-generated method stub
		super.beforeRender();
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		writer.write("<input type=\"hidden\" name=\"" + getId() + "\" id=\"" + getId() + "\" value=\"" + transform(getValue()) + "\" />");
		int pageCode = getPageCode();
		callback.setCallbackParameter(getId(), pageCode + "/" + getPageSize());
		int nextPageCode = pageCode + 1;
		int prevPageCode = pageCode - 1;
		int pageSize = getPageSize();
		if(pageSize == 0)
			pageSize = 10;
		int lastPage = (int)(getTotal() / pageSize);

		if (nextPageCode > lastPage) {
			nextPageCode = lastPage;
		}

		if (prevPageCode < 0) {
			prevPageCode = 0;
		}

		writer.println("<table border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\">");
		writer.println(" <tr>");
		writer.println(" <td>本次查詢共<span>" + getTotal() + "</span>筆</td>");

		if (pageCode > 0) {
			writer.println("<td><img style=\"cursor:pointer\" src=\"" + getPage().getRequest().getContextPath() + "/rbt/resultset_first.png\" width=\"16\" height=\"16\" onclick=\"");

			callback.setCallbackParameter(getId(), 0 + "/" + getPageSize());
			callback.render(writer);
			writer.print("\" /></td>");
			writer.print("<td><img style=\"cursor:pointer\" src=\"" + getPage().getRequest().getContextPath() + "/rbt/resultset_previous.png\" width=\"16\" height=\"16\" onclick=\"");

			callback.setCallbackParameter(getId(), prevPageCode + "/" + getPageSize());
			callback.render(writer);
			writer.print("\" /></td>");
		}

		writer.println("<td>每頁顯示</td>");
		writer.println("<td><select onchange=\"");

		callback.setCallbackParameter(getId(), pageCode + "/'+$(this).fieldValue()+'");
		callback.render(writer);
		writer.println("\">");

		for (int loop = 0; loop < 5; loop++) {
			int runSize = ((loop + 1) * 10);
			if (pageSize == runSize) {
				writer.print("<option selected=\"selected\" value=\"");
				writer.print(String.valueOf(runSize));
				writer.print("\">" + runSize + "筆</option>");

			} else {
				writer.print("<option value=\"");
				writer.print(String.valueOf(runSize));
				writer.print("\">" + runSize + "筆</option>");
			}
		}

		callback.setCallbackParameter(getId(), prevPageCode + "/" + getPageSize());
		writer.println("</select></td>");

		writer.print("<td><select name=\"pageCode\" onchange=\"");
		callback.setCallbackParameter(getId(), "'+$(this).fieldValue()+'/" + getPageSize());
		callback.render(writer);
		writer.print("\">");

		int start = pageCode - 5;
		if (start < 0) {
			start = 0;
		}
		int i = start;
		for (int loop = i; loop < i + 10 && loop <= lastPage; loop++) {

			if (loop == pageCode) {
				writer.print("<option selected=\"selected\" value=\"");
				writer.print(String.valueOf(loop));
				writer.print("\">第" + (loop + 1) + "頁</option>");
			} else {

				writer.print("<option value=\"");
				writer.print(String.valueOf(loop));
				writer.print("\">第" + (loop + 1) + "頁</option>");
			}
	
		}
		writer.println("</select></td>");

		if (pageCode < lastPage) {
			writer.print("<td><img style=\"cursor:pointer\" src=\"" + getPage().getRequest().getContextPath() + "/rbt/resultset_next.png\" width=\"16\" height=\"16\" onclick=\"");

			callback.setCallbackParameter(getId(), nextPageCode + "/" + getPageSize());
			callback.render(writer);
			writer.print("\" /></td>");
			writer.println("<td><img style=\"cursor:pointer\" src=\"" + getPage().getRequest().getContextPath() + "/rbt/resultset_last.png\" width=\"16\" height=\"16\" onclick=\"");
			callback.setCallbackParameter(getId(), lastPage + "/" + getPageSize());
			callback.render(writer);
			writer.print("\" /></td>");
		}
		writer.println("</tr>");
		writer.println("</table>");

	}

	/**
	 * 增新參數
	 * 
	 * @param key
	 * @param value
	 */
	public void setCallbackParameter(String key, String value) {
		callback.setCallbackParameter(key, value);
	}

	public void setCallbackParameter(String key, String[] value) {
		callback.setCallbackParameter(key, value);
	}

	/**
	 * 移除參數
	 * 
	 * @param key
	 */
	public void removeCallbackParameter(String key) {
		callback.removeCallbackParameter(key);
	}

	public List<Component> getUpdateComponents() {
		return callback.getUpdateComponents();
	}

	public void addUpdateComponent(Component cmp) {
		callback.addUpdateComponent(cmp);
	}

	@Override
	protected String transform(Object value) {
		if (value == null)
			return "0/0";
		return value.toString();
	}
}
