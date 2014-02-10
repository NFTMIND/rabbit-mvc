package os.rabbit.parser;

public class Range implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2633473246299618974L;
	private int start;
	private int end;
	
	public Range(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	
	
}
