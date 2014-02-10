package os.rabbit.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class HTMLParser {
	private static HTMLParser PARSER;
	public final static HTMLParser get() {
		if(PARSER == null) {
			PARSER = new HTMLParser();
		}
		return PARSER;
	}
	private HTMLParser() {}
	
	public Tag parse(String path, String encoding) {
		return parse(new File(path), encoding);
	}
	public Tag parse(File object, String encoding) {
		try {
			InputStream in = new FileInputStream(object);
			byte[] b = new byte[in.available()];
			in.read(b);
			String buf = new String(b, encoding);
			in.close();
			
			return doParse(buf);

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	public Tag parse(Class<?> c, String name) {
		try {
		InputStream in = c.getResourceAsStream(name);
		if(in != null) {
			byte[] b = new byte[in.available()];
			in.read(b);
			String buf = new String(b, "utf-8");
			in.close();
			
			return doParse(buf);
		}
		} catch(IOException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}
	
	
	private Tag doParse(String template) {
		
		Context context = new Context(template);
		XMLParser test = new XMLParser(context);
		Tag tag = test.parse(null);

		return tag;
	}
	public Tag parse(InputStream in) {
		try {

			byte[] b = new byte[in.available()];
			in.read(b);
			String buf = new String(b, "utf-8");
			in.close();
			
			return doParse(buf);

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
