package os.rabbit.components;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import os.rabbit.IRequestCycleListener;
import os.rabbit.parser.Tag;

public class ListBuffer extends Component {
	private String[] css;
	private boolean isTableRow = false;
	private int columnCount = 0;
	private boolean isRootListBuffer = true;

	
	private String emptyDataMessage;
	public ListBuffer(Tag tag) {
		super(tag);

	}

	public String getEmptyDataMessage() {
		return emptyDataMessage;
	}

	public void setEmptyDataMessage(String emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}

	@Override
	protected void initial() {
		isTableRow = getTag().getName().toUpperCase().equals("TR");
		if (isTableRow) {
			List<Tag> childs = getTag().getChildrenTags();
			for (Tag child : childs) {
				if (child.getName().toUpperCase().equals("TD")) {
					columnCount++;
				}
			}
		}
		String cssValue = getTag().getAttribute("rabbit:css");
		if (cssValue != null) {
			css = cssValue.split(",");
			// setTagAttributeModifier("class", true);

		}

		getPage().addRequestCycleListener(new IRequestCycleListener() {

			@Override
			public void requestStart(HttpServletRequest request, HttpServletResponse response) {
				setAttribute("buffer", new StringWriter());
			}

			@Override
			public void requestEnd(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			}
		});

		checkRoot();
	}

	private void checkRoot() {
		Component parent = getParent();
		while (parent != null) {
			if (parent instanceof ListBuffer) {
				isRootListBuffer = false;
			}
			parent = parent.getParent();
		}
	}

	public PrintWriter getWriter() {
		return new PrintWriter((StringWriter) getAttribute("buffer"));
	}

	@Override
	public void render(PrintWriter writer) {
		int index = getIndex();
		StringWriter buffer = (StringWriter) getAttribute("buffer");
		if (index == 0 && isTableRow && emptyDataMessage != null) {
			writer.print("<tr><td colspan=\"");
			writer.print(columnCount);
			writer.print("\">");
			writer.print(emptyDataMessage);
			writer.print("</td></tr>");
		} else {
			writer.print(buffer.toString());
		}
		if (!isRootListBuffer)
			buffer.getBuffer().delete(0, buffer.getBuffer().length());
	}

	private int getIndex() {
		Integer index = (Integer) getAttribute("index");
		if (index == null)
			return 0;

		return index;
	}

	private void setIndex(int index) {
		setAttribute("index", index);
	}

	public void flush() {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		// StringWriter buffer = (StringWriter)getAttribute("buffer");
		int index = getIndex();
		if (css != null) {
			setTagAttribute("class", css[index % css.length]);

		}
		index++;
		setIndex(index);
		super.render(writer);
		writer.flush();

		StringWriter buffer = (StringWriter) getAttribute("buffer");
		buffer.write(sw.toString());

		visit(new IComponentVisitor() {

			@Override
			public boolean visit(Component component) {
				component.updateRenderId();
				return true;
			}
		});

	}
}
