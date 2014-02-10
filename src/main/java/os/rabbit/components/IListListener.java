package os.rabbit.components;

import java.io.PrintWriter;

@Deprecated
public interface IListListener<T> {

	/**
	 * 
	 * @param writer TODO
	 * @param object
	 * @param index
	 */
	public void beforeEachRender(PrintWriter writer, T object, int index);
	
	
	/**
	 * @param writer TODO
	 * @param object
	 * @param index
	 */
	public void afterEachRender(PrintWriter writer, T object, int index);


	public void emptyRender(PrintWriter writer);
	
}
