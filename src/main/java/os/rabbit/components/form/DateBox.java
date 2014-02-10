package os.rabbit.components.form;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class DateBox extends FormComponent<Date> {
	private String pattern = "yyyy/MM/dd";
	private SimpleDateFormat format = new SimpleDateFormat(pattern);
	public DateBox(Tag tag) {
		super(tag);
		new AttributeModifier(this, "value", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				Date value = getValue();
				if(value != null) {
					
					writer.write(format.format(value));
				}
			}
			
		});
		
		
	}
	
	@Override
	protected void afterBuild() {
		getPage().addCSSImport("jQueryUICSS", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/jqueryui/themes/base/jquery-ui.min.css");
			}
		});
		getPage().addScriptImport("jQueryUI", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/jqueryui/jquery-ui.min.js");
			}
		});
		getPage().addScriptImport("jQueryUI-i18n-zh-TW", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRelativelyRoot() + "/rbt/jqueryui/i18n/jquery-ui-i18n.min.js");
			}
		});
		
		
		getPage().addScript("DateBox$" + getId(), new IRender() {
			
			@Override
			public void render(PrintWriter writer) {
				writer.println("$(document).ready(function() {");
				writer.println("	$('#"+getId()+"').datepicker($.datepicker.regional['zh-TW']);");
				writer.println("	$('#"+getId()+"').datepicker(\"option\", \"dateFormat\", \""+pattern.replace("yy", "y").replace("M", "m")+"\");");
				writer.println("});");
			}
		});
	}
	
	
	public String getPattern() {
		return pattern;
	
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
		format = new SimpleDateFormat(pattern);
	}


	@Override
	protected Date transform(Object value) {
		if(value == null) return null;
		
		try {
			return format.parse(value.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}