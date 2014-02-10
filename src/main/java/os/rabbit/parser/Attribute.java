package os.rabbit.parser;


public class Attribute implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706245124032506447L;
	
	private int start;
	private int end;
	private String name;
	private String value;
	
	private int valueStart;
	private int valueEnd;
	
	private int nameStart;
	private int nameEnd;
	private int index;
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public String getValue() {
		if(value != null) {
			value = value.replace("&lt;", "<");
			value = value.replace("&gt;", ">");
		}
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getValueEnd() {
		return valueEnd;
	}
	public void setValueEnd(int valueEnd) {
		this.valueEnd = valueEnd;
	}
	public int getValueStart() {
		return valueStart;
	}
	public void setValueStart(int valueStart) {
		this.valueStart = valueStart;
	}
	public int getNameEnd() {
		return nameEnd;
	}
	public void setNameEnd(int nameEnd) {
		this.nameEnd = nameEnd;
	}
	public int getNameStart() {
		return nameStart;
	}
	public void setNameStart(int nameStart) {
		this.nameStart = nameStart;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
}