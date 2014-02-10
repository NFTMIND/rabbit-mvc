package os.rabbit.components;

import java.util.List;

public interface ITreeDataProvider<T> {
	public boolean isExpand(T object);
	public boolean hasChildren(T parent);
	
	public List<T> getChildrens(T parent);
	
	public String getId(T object);
	public T getNode(String id);
}
