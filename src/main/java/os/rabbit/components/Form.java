package os.rabbit.components;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import os.rabbit.Help;
import os.rabbit.IModifier;
import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.StringRender;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Range;
import os.rabbit.parser.Tag;

public class Form extends Component implements ITrigger {

	private List<IFormListener> listeners = new LinkedList<IFormListener>();

	public Form(Tag tag) {
		super(tag);
	}

	

	@Override
	protected void initial() {
		final Component container = getContainer();
		if (container != null) {
			Class<? extends Component> c = container.getClass();
			final Method method = Help.getMethod(c, getId() + "$onSubmit");

			if (method != null) {
				addFormListener(new IFormListener() {

					@Override
					public void submit() {
						try {
							method.setAccessible(true);
							method.invoke(container);
						} catch (Exception e) {

							e.printStackTrace();

						}
					}
				});
			}

		}
		getPage().addTrigger(getId() + "$formSubmit", this);
		new AttributeModifier(this, "action", "?");

		int start = Form.this.getTag().getStart();
		int end = start;
		final Range range = new Range(start, end);

		start = Form.this.getTag().getBodyStart();
		end = start;
		final Range range1 = new Range(start, end);

		addModifier(new IModifier() {

			@Override
			public void render(PrintWriter writer) {
				writer.write("<input type=\"hidden\" name=\"triggerId\" value=\"" + getId() + "$formSubmit\" />");
				writer.write("<input type=\"hidden\" name=\"rbtType\" value=\"INVOKE\" />");
				HashMap<String, Object> map = (HashMap<String, Object>) getAttribute("CALLBACK_PARAMETERS");
				if (map != null) {
					for (String key : map.keySet()) {
						writer.write("<input type=\"hidden\" name=\"" + key + "\" value=\"" + map.get(key) + "\" />");

					}
				}
			}

			@Override
			public Range getRange() {

				return range1;
			}
		});

		addModifier(new IModifier() {

			@Override
			public void render(PrintWriter writer) {
				writer.write("<div style=\"color:#FF0000\">");
				for (IRender messageRender : getTracks()) {
					writer.write("<li>");
					messageRender.render(writer);
					writer.write("</li>");
				}
				writer.write("</div>");

			}

			@Override
			public Range getRange() {

				return range;
			}
		});
	}

	public void addFormListener(IFormListener listener) {
		listeners.add(listener);
	}

	@Override
	protected void beforeRender() {

	}

	@Override
	public void invoke() {

		visit(new IComponentVisitor() {
			@Override
			public boolean visit(Component component) {
				if (component instanceof FormComponent<?>) {
					FormComponent<?> formCmp = (FormComponent<?>) component;
					formCmp.validate(Form.this);
				}
				return true;
			}
		});

		if (getTracks().size() == 0) {
			for (IFormListener listener : listeners) {
				listener.submit();
			}
		}
	}

	public List<IRender> getTracks() {
		HttpServletRequest req = getPage().getRequest();
		List<IRender> tracks = (List<IRender>) req.getAttribute(getId() + "_tracks");
		if (tracks == null) {
			tracks = new LinkedList<IRender>();
			req.setAttribute(getId() + "_tracks", tracks);
		}
		return tracks;
	}

	public void error(IRender messageRender) {
		List<IRender> tracks = getTracks();
		tracks.add(messageRender);
	}

	public void error(String message) {
		error(new StringRender(getPage(), message));
	}

	public void setCallbackParameter(String key, Object value) {
		// uriInvokeModifier.setCallbackParameter(key, value.toString());

		HashMap<String, Object> map = (HashMap<String, Object>) getAttribute("CALLBACK_PARAMETERS");
		if (map == null) {
			map = new HashMap<String, Object>();
			setAttribute("CALLBACK_PARAMETERS", map);
		}
		map.put(key, value);
	}
}
