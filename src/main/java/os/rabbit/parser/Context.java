package os.rabbit.parser;

import java.util.ArrayList;
import java.util.List;

public class Context {
	private List<String> BEGIN_SIGNS = new ArrayList<String>();

	private List<String> END_SIGNS = new ArrayList<String>();

	private String template;

	private int index;
	public Context(String template) {
		this.template = template;
		BEGIN_SIGNS.add("<?xml");

		BEGIN_SIGNS.add("<!DOCTYPE");

		
		BEGIN_SIGNS.add("<!--");
		BEGIN_SIGNS.add("</");
		BEGIN_SIGNS.add("<");

		END_SIGNS.add("?>");
		END_SIGNS.add("/>");
		END_SIGNS.add(">");
		END_SIGNS.add("-->");

	}

	public String getTemplate() {
		return template;
	}
	public void moveUntil(String sign) {
		for (; index < template.length(); index++) {

			if (index + sign.length() < template.length()) {
				if (sign.equals(template.substring(index, index + sign.length()))) {
					return;
				}
			}
		}

	}
	public void moveUntilUnequal(String sign) {
		for (; index < template.length(); index++) {

			if (index + sign.length() < template.length()) {
				if (!sign.equals(template.substring(index, index + sign.length()))) {
					return;
				}
			}
		}

	}
	public void moveUntilCharOREndToken() {
		for (; index < template.length(); index++) {

			if (index < template.length()) {
				if (template.charAt(index) != ' ') {

					return;
				} else {
					for (String endSign : END_SIGNS) {
						if (index + endSign.length() < template.length()) {
							if (endSign.equals(template.substring(index, index + endSign.length()))) {
								return;
							}
						}
					}
				}

			}
		}

	}
	public String moveUntilOREndToken(String sign) {
		for (; index < template.length(); index++) {

			if (index + sign.length() < template.length()) {
				if (sign.equals(template.substring(index, index + sign.length()))) {

					return sign;
				} else {
					for (String endSign : END_SIGNS) {
						if (index + endSign.length() < template.length()) {
							if (endSign.equals(template.substring(index, index + endSign.length()))) {
								return endSign;
							}
						}
					}
				}

			}
		}
		return null;
	}

	public String moveBeginToken() {
		for (; index < template.length(); index++) {

			for (String sign : BEGIN_SIGNS) {
				if (index + sign.length() < template.length()) {
					if (sign.equals(template.substring(index, index + sign.length())))
						return sign;
				}
			}
		}
		return null;
	}

	public String moveEndToken() {
		for (; index < template.length(); index++) {

			for (String sign : END_SIGNS) {
				if (index + sign.length() < template.length()) {
					if (sign.equals(template.substring(index, index + sign.length()))) {
						index += sign.length();
						return sign;
					}
				}
			}
		}
		return null;
	}
	
	public void skip(int space) {
		index += space;
	}

	public int getCurrentIndex() {
		return index;
	}

	public int getCurrentLine() {
		int line = 0;
		for(int loop = 0; loop < index; loop++) {
			if(template.charAt(loop) == 13) {
				line++;
			}
		}
		return line;
	}


	public void setCurrentIndex(int index) {
		this.index = index;
	}

	public String getCurrentLineContext() {
		int s = template.lastIndexOf("\r\n", index);
		int e = template.indexOf("\r\n", index);
		if(s == -1) {
			s = 0;
		} else {
			s += 2;
		}
		
		if(e == -1) {
			e = template.length();
		}
		return template.substring(s, e);
	}

	public int getCurrentLineStart() {
		int s = template.lastIndexOf("\r\n", index);
		if(s == -1) {
			s = 0;
		} else {
			s += 2;
		}
		return s;
		
	}

	public int getCurrentLineEnd() {
		int e = template.indexOf("\r\n", index);
		if(e == -1) {
			e = template.length();
		}
		return e;
	}


	
	
}