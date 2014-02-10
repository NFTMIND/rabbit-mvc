package os.rabbit;

import java.io.PrintWriter;

import os.rabbit.components.WebPage;

public class StringRender implements IRender {
	private WebPage page;
	private String value;
	public StringRender(WebPage page, String value) {
		this.page = page;
		this.value = value;
	}
	@Override
	public void render(PrintWriter writer) {
		String word = page.transalte(value);
		writer.write(word);
	}
	public String getValue() {
		return value;
	}

}
