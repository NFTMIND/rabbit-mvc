package os.rabbit.components;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import os.rabbit.IRequestCycleListener;
import os.rabbit.parser.Tag;

public class ListBuffer extends Component {

	public ListBuffer(Tag tag) {
		super(tag);

	}
	
	private String[] css;
	@Override
	protected void afterBuild() {
		String cssValue = getTag().getAttribute("rabbit:css");
		if(cssValue != null) {
			css = cssValue.split(",");
			//setTagAttributeModifier("class", true);
			
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
	private boolean isRootListBuffer = true;
	private void checkRoot() {
		Component parent = getParent();
		while(parent != null) {
			if(parent instanceof ListBuffer) {
				isRootListBuffer = false;
			}
			parent = parent.getParent();
		}
	}
	
	public PrintWriter getWriter() {
		return new PrintWriter((StringWriter)getAttribute("buffer"));
	}
	
	@Override
	public void render(PrintWriter writer) {
		StringWriter buffer = (StringWriter)getAttribute("buffer");
		writer.write(buffer.toString());
		
		if(!isRootListBuffer)
			buffer.getBuffer().delete(0, buffer.getBuffer().length());
	}
	
	private int getIndex() {
		Integer index = (Integer)getAttribute("index");
		if(index == null) return 0;
		
		return index;
	}
	private void setIndex(int index) {
		setAttribute("index", index);
	}
	public void flush() {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		//StringWriter buffer = (StringWriter)getAttribute("buffer");
		int index = getIndex();
		if(css != null) {
			setTagAttribute("class", css[index % css.length]);
			
		}
		index++;
		setIndex(index);
		super.render(writer);
		writer.flush();
		
		StringWriter buffer = (StringWriter)getAttribute("buffer");
		buffer.write(sw.toString());

		
	}
}
