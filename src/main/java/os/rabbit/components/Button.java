package os.rabbit.components;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;

import os.rabbit.Help;
import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.IScriptInvokeCallback;
import os.rabbit.components.form.FormComponent;
import os.rabbit.modifiers.ScriptInvokeModifier;
import os.rabbit.parser.Tag;

public class Button extends Component implements ITrigger, IScriptInvokeCallback {
	private LinkedList<IButtonListener> listeners = new LinkedList<IButtonListener>();
	private ScriptInvokeModifier modifier;

	public Button(Tag tag) {
		super(tag);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void afterBuild() {

				modifier = createModifier();
				getPage().addTrigger(getId(), Button.this);
				final Component component = getContainer();
				if (component != null) {
					Class<? extends Component> c = component.getClass();

					final Method method = Help.getMethod(c, getId() + "$onClick");
					if (method != null) {

						addButtonListener(new IButtonListener() {

							@Override
							public void click() {
								try {
									method.setAccessible(true);
									method.invoke(component);
								} catch (Exception e) {

									e.printStackTrace();

								}
							}
						});

					}
					Field field = Help.getField(c, getId());
					if (field != null) {
						UpdateComponent uc = field.getAnnotation(UpdateComponent.class);
						if (uc != null) {
							String[] fieldNames = uc.value();
							for (String fieldName : fieldNames) {
								Field updateField = Help.getField(c, fieldName);
								
								if(updateField != null) {
									updateField.setAccessible(true);
									try {
										Object fieldObj = updateField.get(component);
										if (!(fieldObj instanceof FormComponent)) {
											throw new RuntimeException(getId() + " must be subclass of FormCompoent");
										}
						
										addUpdateComponent((FormComponent) fieldObj);
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								
								}
								
							}
					

						}
					}
				
				}

			}
		});
	}

	protected ScriptInvokeModifier createModifier() {
		return new ScriptInvokeModifier(this, "onclick", this);
	}

	public void setURI(String uri) {
		modifier.setURI(uri);
	}

	public void setConfirm(IRender render) {

	}

	public void setConfirm(String message) {

		if (modifier instanceof ScriptInvokeModifier) {

			ScriptInvokeModifier scriptModifier = (ScriptInvokeModifier) modifier;
			scriptModifier.setConfirm(message);
		}
	}

	public void addButtonListener(IButtonListener listener) {
		listeners.add(listener);
	}

	@Override
	public void invoke() {
		for (IButtonListener listener : listeners) {
			listener.click();
		}
	}

	/**
	 * 增新參數
	 * 
	 * @param key
	 * @param value
	 */
	public void setCallbackParameter(String key, String value) {
		modifier.setCallbackParameter(key, value);
	}

	public void setCallbackParameter(String key, String[] value) {
		modifier.setCallbackParameter(key, value);
	}

	/**
	 * 移除參數
	 * 
	 * @param key
	 */
	public void removeCallbackParameter(String key) {
		modifier.removeCallbackParameter(key);

	}

	public void addUpdateComponent(Component cmp) {
		modifier.addUpdateComponent(cmp);
	}

}
