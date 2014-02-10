package os.rabbit.callbacks;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import os.rabbit.IRender;
import os.rabbit.components.Component;
import os.rabbit.components.ComponentAdapter;
import os.rabbit.modifiers.AttributeModifier;

public class PopupMenuModifier extends AttributeModifier {

	private Component component;

	public PopupMenuModifier(final Component component, final String name) {

		super(component, name, new IRender() {
			public List<PopupMenuItem> getItems() {
				List<PopupMenuItem> map = (LinkedList<PopupMenuItem>) component.getAttribute("items");
				if (map == null) {
					map = new LinkedList<PopupMenuItem>();
					component.setAttribute("items", map);
				}
				return map;
			}

			@Override
			public void render(PrintWriter writer) {
				writer.write("handlePopupMenu(this, event, ");
				writer.write("[");

				List<PopupMenuItem> items = getItems();
				for (PopupMenuItem item : items) {
					writer.write("{");
					writer.write("	name:'" + item.name + "',");
					writer.write("	handler:function(popupMenu) {");
					item.callbackRender.render(writer);
					writer.write("	}");
					writer.write("},");
				}

				writer.write("]");
				writer.write(")");
				
				if(name.equalsIgnoreCase("oncontextmenu")) {
					writer.write("; return false;");
				}
			}
		});
		this.component = component;
		if (component.getPage() == null)
			component.addComponentListener(new ComponentAdapter() {
				@Override
				public void initial() {
					register();
				}
			});
		else
			register();
	
	}

	private void register() {
		component.getPage().addScriptImport("popupMenu", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(component.getPage().getRequest().getContextPath() + "/rbt/popupmenu.js");
			}
		});
		component.getPage().addScript("popupMenu", new IRender() {

			@Override
			public void render(PrintWriter writer) {

				writer.println("function handlePopupMenu(component, e, items) {");

				writer.println("var popupMenu = new PopupMenu();");

				writer.println("for(var i = 0; i < items.length; i++) {");
				writer.println("	if(items[i].name == 'SEPARATOR') {");
				writer.println("		popupMenu.addSeparator();");
				writer.println("	} else {");
				writer.println("		popupMenu.add(items[i].name, items[i], function(data) {");
				writer.println("			data.handler(popupMenu);");
				writer.println("		});");
				writer.println("	}");
				writer.println("}");

				writer.println("popupMenu.show(e)");
				writer.println("PopupMenu.addEventListener(document, \"click\", ");
				writer.println("function() { ");
				writer.println("	popupMenu.hide();");

				writer.println("}, true);");

				writer.println("}");
			}
		});
	}

	public List<PopupMenuItem> getItems() {
		List<PopupMenuItem> map = (LinkedList<PopupMenuItem>) component.getAttribute("items");
		if (map == null) {
			map = new LinkedList<PopupMenuItem>();
			component.setAttribute("items", map);
		}
		return map;
	}

	public void addSeparator() {
		PopupMenuItem item = new PopupMenuItem("SEPARATOR", new IRender() {
			@Override
			public void render(PrintWriter writer) {
			}
		});
		getItems().add(item);
	}

	public void addItem(final String name, final IRender render) {
		PopupMenuItem item = new PopupMenuItem(name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				render.render(writer);
			}
		});

		getItems().add(item);
	}

	public void addURLItem(final String name, final String url) {
		PopupMenuItem item = new PopupMenuItem(name, new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.print("location.href='" + url + "';");
			}
		});
		getItems().add(item);
	}

//	public void addCallbackItem(final String name, final ICallback callback) {
//		PopupMenuItem item = new PopupMenuItem(name, new IRender() {
//			@Override
//			public void render(PrintWriter writer) {
//				callback.render(writer);
//			}
//		});
//		getItems().add(item);
//	}

	public void clearItem() {
		getItems().clear();
	}

	public void removeItem(String name) {
		LinkedList<PopupMenuItem> removed = new LinkedList<PopupMenuItem>();
		for(PopupMenuItem item : getItems()) {
			if(item.name.equals(name)) {
				removed.add(item);
			}
		}
		for(PopupMenuItem item : removed) {
			getItems().remove(item);
		}
	}
}
