package os.rabbit.components;

import java.io.PrintWriter;

public interface ITreeListener<T> {

	public void beforeNodeRender(PrintWriter writer, T node);
	public void afterNodeRender(PrintWriter writer, T node);
	
}
