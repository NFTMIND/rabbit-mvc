package os.rabbit.components;

import java.io.PrintWriter;
import java.util.Collection;

import os.rabbit.parser.Tag;
@Deprecated
public class ComponentList<T> extends Component {
	private IListListener<T> listener;

	// private Collection<T> datas;

	public ComponentList(Tag tag) {
		super(tag);
	}

	public void setCollection(Collection<T> datas) {
		setAttribute("datas", datas);
	}
	public Collection<T> getCollection() {
		
		return (Collection<T>)getAttribute("datas");
	}
	public void setListListener(IListListener<T> listener) {
		this.listener = listener;
	}
	private String rabbitCss = null;
	private String[] cssArray = null;
	
	@Override
	protected void afterBuild() {
		
		rabbitCss = getTag().getAttribute("rabbit:css");
		if(rabbitCss != null) {
			//setTagAttributeModifier("class", true);
			cssArray = rabbitCss.split(",");
		}
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		int index = 0;
		Collection<T> datas = (Collection<T>) getAttribute("datas");
		if (datas != null) {
			if (datas.size() == 0) {
				listener.emptyRender(writer);
			} else
				for (T each : datas) {
					if(cssArray != null) {
						int cssIndex = index % cssArray.length;
						setTagAttribute("class", cssArray[cssIndex]);
					}
					if (listener != null)
						listener.beforeEachRender(writer, each, index);

					super.renderComponent(writer);

					if (listener != null)
						listener.afterEachRender(writer, each, index);

					index++;
				}
		} else {
			if (listener != null)
				listener.emptyRender(writer);

		}
	}

}
