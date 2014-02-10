package os.rabbit.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class XMLParser {
	private Context context;

	public static void main(String[] args) {
		File file = new File("C:\\abc.xml");
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			XMLParser s = new XMLParser(new Context(new String(buf, "utf-8")));
			Tag tag = s.parse();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public XMLParser(Context context) {
		this.context = context;

	}

	/**
	 * 
	 * @param file 
	 * 檔案
	 * @param encoding
	 * 編碼
	 * @throws IOException
	 */
	public XMLParser(File file, String encoding) throws IOException {
		this(new FileInputStream(file), encoding);
	}
	public XMLParser(InputStream in, String encoding) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
		StringBuffer buffer = new StringBuffer();
		while(true) {
			String line = reader.readLine();
			if(line == null)
				break;
			
			buffer.append(line);
			buffer.append("\r\n");
		}
		reader.close();
		context = new Context(buffer.toString());
		
	}
	/**
	 * @param path
	 * 路徑
	 * @param encoding
	 * 編碼
	 * @throws IOException
	 */
	public XMLParser(String path, String encoding) throws IOException {
		this(new File(path), encoding);
	}

	public String getTemplate() {
		return context.getTemplate();
	}
	
	private void parseAttributes(Tag tag) {
		int s = context.getCurrentIndex();
		String sign = context.moveUntilOREndToken(" ");
		int e = context.getCurrentIndex();
		while((sign != null) && (sign != ">") && (sign != "/>")) {
			
			context.moveUntilUnequal(" ");

			s = context.getCurrentIndex();
			
			sign = context.moveUntilOREndToken("=");
			if((sign == null) || (sign == ">") || (sign == "/>")) return;
			
			
			
			e = context.getCurrentIndex();
			
			Attribute attribute = new Attribute();
			attribute.setStart(s);

			
			if(s == e) break;
			
			String name = context.getTemplate().substring(s, e);

			attribute.setNameStart(s);
			attribute.setNameEnd(e);
			attribute.setName(name);
			
			sign = context.moveUntilOREndToken("\"");
			context.skip(sign.length());
			s = context.getCurrentIndex();
			
			sign = context.moveUntilOREndToken("\"");

			e = context.getCurrentIndex();
			String value = context.getTemplate().substring(s, e);
			attribute.setValueStart(s);
			attribute.setValueEnd(e);
			attribute.setValue(value);
			
			attribute.setEnd(e + 1);

			tag.addAttribute(name, attribute);

			sign = context.moveUntilOREndToken(" ");
			e = context.getCurrentIndex();
		}

	}
	
	public Tag parseTag(Tag parent) {
		int s = context.getCurrentIndex() + 1;
		context.moveUntilOREndToken(" ");
		int e = context.getCurrentIndex();
		
		Tag tag = new Tag(parent, context.getTemplate().substring(s, e), s, e);
		tag.setStart(s - 1);
		tag.setNameStart(s);
		tag.setNameEnd(e);
		tag.setTemplate(context.getTemplate());
		
		return tag;
	}

	public Tag parse(Tag parent) {
		Tag root = parent;
		String sign = context.moveBeginToken();
		while (sign != null) {

			if (sign.equals("<!DOCTYPE")) {
				context.moveEndToken();
			} else if(sign.equals("<?xml")) {
				context.moveUntil("?>");
				context.skip(2);

			} else if (sign.equals("<")) {
				int s = context.getCurrentIndex();
				Tag tag = parseTag(parent);
				

				parseAttributes(tag);

				String endToken = context.moveEndToken();
				int e = context.getCurrentIndex();

				if(endToken == ">") {
					tag.setBodyStart(e);
					tag.setEndTagRange(new Range(s, e - 1));
					if(tag.getName().equals("script")) {
						context.moveUntil("</script>");
						root = parse(tag);
					} else {
						root = parse(tag);
					}
					tag.setEnd(context.getCurrentIndex());
				} else if(endToken == "/>") {
					tag.setClosedSignStart(s);
					tag.setClosedSignEnd(e);
					tag.setBodyStart(-1);
					tag.setBodyEnd(-1);
					tag.setEnd(e);
					
					tag.setEndTagRange(new Range(e - 2, e));
				} else {
					tag.setEnd(context.getCurrentIndex());
				}
				

			} else if (sign.equals("</")) {
				int s = context.getCurrentIndex();
				context.moveEndToken();
				int e = context.getCurrentIndex();
				
				String tagName = context.getTemplate().substring(s + 2, e - 1);

				if(!tagName.equals(parent.getName())) {
					parent.setClosedSignStart(s);
					parent.setClosedSignEnd(s);
					
					parent.setBodyEnd(parent.getBodyStart());
					
					//
					context.setCurrentIndex(s);
					
					String value = context.getCurrentLineContext();
					int cs = context.getCurrentLineStart();

					StringBuffer errorMsg = new StringBuffer("\r\n\"" + value + "\" Line:" + (context.getCurrentLine() + 1) + "\r\n");
					for(int loop = cs; loop < context.getCurrentIndex() + 1; loop++) {
						errorMsg.append(" ");
					}
					for(int loop = context.getCurrentIndex(); loop <  context.getCurrentLineEnd(); loop++) {
						errorMsg.append("^");
					}

					
					
					throw new IllegalStateException(errorMsg.toString());
					
					
				} else {
					

					parent.setBodyEnd(s);
					parent.setClosedSignStart(s);
					parent.setClosedSignEnd(e);

				}
				return parent;

			} else if (sign.equals("<!--")) {
				context.moveUntil("-->");
				context.skip(3);
			}
			sign = context.moveBeginToken();
			
			
		}
		return root;
	}

	public Tag parse() {
		return parse(null);
		
	}

}
