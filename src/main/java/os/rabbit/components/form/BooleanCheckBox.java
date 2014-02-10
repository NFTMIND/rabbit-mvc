package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class BooleanCheckBox extends FormComponent<Boolean> {

	public BooleanCheckBox(Tag tag) {
		super(tag);
//		new AttributeModifier(this, "onchange", new IRender() {
//			
//			@Override
//			public void render(PrintWriter writer) {
//				writer.write("$(this).val($(this).is(':checked'))");
//			}
//		});
		
		
		//new AttributeModifier(this, "type", "hidden");
		
		new AttributeModifier(this, "forceUpdate", "true");
		new AttributeModifier(this, "checked", "checked") {
			@Override
			protected void renderAttribute(String name, IRender valueRender, PrintWriter writer) {
				Boolean value = getValue();
				if (value != null && value) {
					super.renderAttribute(name, valueRender, writer);
				}
			};
		};
		

	
		 new AttributeModifier(this, "value", new IRender() {
				@Override
				public void render(PrintWriter writer) {
					Boolean value = getValue();
					if (value != null) {
						writer.write(value.toString());
					} else {
						writer.write("false");
					}
				}

			});

	}
	
	@Override
	protected void afterBuild() {
	
		getPage().addScript("BooleanCheckBox$" + getId(), new IRender() {
			
			@Override
			public void render(PrintWriter writer) {
				writer.println("$(document).ready(function() {");
				writer.println("	$(\"[id=\\\""+getId()+"\\\"]\").bind(\"change\",function(event) {");
				writer.println("	$(event.target).val($(this).is(':checked'));");
				writer.println("	});");
				writer.println("});");
			}
		});
	}

	@Override
	public Boolean getValue() {

		Boolean value = super.getValue();
		if (value == null) {
			return false;
		}
		return value;
	}

	@Override
	protected Boolean transform(Object value) {

		if (value == null)
			return false;

		if (value.equals("null")) {
			return false;
		}

		return Boolean.parseBoolean(value.toString());
	}

//	@Override
//	public void renderComponent(PrintWriter writer) {
//		super.renderComponent(writer);
//		writer.write("<input id=\"" + getId() + "_ctrl\" type=\"checkbox\"");
//		if(getValue()) {
//			writer.write(" checked=\"checked\"");
//		}
//		writer.write(" onchange=\"$('#"+getId()+"').val($(this).is(':checked'))\" />");
//	}

}
