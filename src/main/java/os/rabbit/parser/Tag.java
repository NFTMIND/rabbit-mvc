package os.rabbit.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public 	class Tag implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7035385916066978159L;
	private Tag parentTag;
	private int start;
	private int end;
	
	private int nameStart;
	private int nameEnd;
	
	private int bodyStart;
	private int bodyEnd;
	
	private int closedSignStart;
	private int closedSignEnd;
	
	private String template;

	private String name;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	private List<Tag> childrens = new LinkedList<Tag>();
	
	private int index;
	
	private int attributeSize = 0;
	private int childrenTagSize = 0;
	
	
	public Tag(Tag parent, String tagName, int startPos, int endPos) {
		parentTag = parent;
		name = tagName;
		start = startPos;
		end = endPos;
		if(parentTag != null) {
			parentTag.addChildrenTag(this);
		}
	}
	
	public boolean hasBody() {
		return bodyStart != -1 && bodyEnd != -1;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Attribute> getAttributes() {
		return attributes;
	}
	
	public String getAttribute(String name) {
		Attribute attr = attributes.get(name);
		if(attr == null) return null;
		
		return attr.getValue();
	}
	public Integer getAttributeAsInteger(String name) {
		String val = getAttribute(name);
		if(val == null) return null;
		return Integer.parseInt(val);
	}
	public Float getAttributeAsFloat(String name) {
		String val = getAttribute(name);
		if(val == null) return null;
		return Float.parseFloat(val);
	}
	public Double getAttributeAsDouble(String name) {
		String val = getAttribute(name);
		if(val == null) return null;
		return Double.parseDouble(val);
	}
	public Boolean getAttributeAsBoolean(String name) {
		String val = getAttribute(name);
		if(val == null) return null;
		return Boolean.parseBoolean(val);
	}
	public Long getAttributeAsLong(String name) {
		String val = getAttribute(name);
		if(val == null) return null;
		return Long.parseLong(val);
	}
	
	
//	public void setAttributes(Map<String, Attribute> attributes) {
//		this.attributes = attributes;
//	}
	
	public void addAttribute(String name, Attribute attr) {
		attributes.put(name, attr);
		attr.setIndex(attributeSize);
		attributeSize++;
	}
	
	public Tag getParentTag() {
		return parentTag;
	}
	public void setParentTag(Tag parentTag) {
		this.parentTag = parentTag;
	}
	
	
	public void addChildrenTag(Tag tag) {
		childrens.add(tag);
		tag.setIndex(childrenTagSize);
		tag.setParentTag(this);
		childrenTagSize++;
	}
	
	public List<Tag> getChildrenTags() {
		return childrens;
	}
	public int getBodyEnd() {
		return bodyEnd;
	}
	public void setBodyEnd(int bodyEnd) {
		this.bodyEnd = bodyEnd;
	}
	public int getBodyStart() {
		return bodyStart;
	}
	public void setBodyStart(int bodyStart) {
		this.bodyStart = bodyStart;
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
	public int getClosedSignEnd() {
		return closedSignEnd;
	}
	public void setClosedSignEnd(int closedSignEnd) {
		this.closedSignEnd = closedSignEnd;
	}
	public int getClosedSignStart() {
		return closedSignStart;
	}
	public void setClosedSignStart(int closedSignStart) {
		this.closedSignStart = closedSignStart;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getAttributeSize() {
		return attributeSize;
	}
	public void setAttributeSize(int attributeSize) {
		this.attributeSize = attributeSize;
	}
	public int getChildrenTagSize() {
		return childrenTagSize;
	}
	public void setChildrenTagSize(int childrenTagSize) {
		this.childrenTagSize = childrenTagSize;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	
	private Range startTagRange;
	private Range endTagRange;
	
	public Range getEndTagRange() {
		return endTagRange;
	}

	public void setEndTagRange(Range range) {
		endTagRange = range;
	}

	public Range getStartTagRange() {
		return startTagRange;
	}

	public void setStartTagRange(Range startTagRange) {
		this.startTagRange = startTagRange;
	}

	public void visit(ITagStructureVisitor visitor) {
		visitor.visit(this);
		for(Tag child : new ArrayList<Tag>(childrens)) {
			child.visit(visitor);
		}
	}

	public String getBody() {
		// TODO Auto-generated method stub
		return getTemplate().substring(bodyStart, bodyEnd);
	}
	


}


