In the chapter, we will introduce the core of Rabbit "Modifier".
It is very important.
You have to pay attention to every detail in the chapter.

This chapter have three section:
> [HTML tag attribute modifier](#HTML_tag_attribute_modifier.md)

> [Javascript event callback](#Javascript_event_callback.md)

> [Advanced Modifier](#Advanced_Modifier.md)

# HTML tag attribute modifier #

If you want to modify tag attribute, you have three way:
  1. Use ELComponent
  1. Use AttributeModifier
  1. Invoke setTagAttribute() method of Component.

In this section, we will teach you how to use AttributeModifier to modify tag attribute.
## AttributeModifier ##

This component is easy to use, we will demo it as following:
```
package os.rabbit.demo;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.parser.Tag;

public class AttributeModifierDemo extends Component {
	private Label label;
	private Button changeColor;
	public AttributeModifierDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {

		new AttributeModifier(this, "style", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				String color = (String)getAttribute("color");
				if(color != null)
					writer.write("color:" + color);
			}
		});
		
		changeColor.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				setAttribute("color", "#FF0000");
			}
		});
	}
	
	@Override
	protected void beforeRender() {
		label.setValue("Demo text");
	}

}

```

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>AttributeModifier Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.AttributeModifierDemo">
	<span rabbit:id="label">Text</span>
	<br />
	<input type="button" rabbit:id="changeColor" value="Click me to change color" />
</body>
</html>
```

Run result as following:

initial screen

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_007.jpg' />

after click button

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_008.jpg' />

We use AttributeModifier to change the text color.
After click the button, text color will be changed.
We actually is to change the text CSS syntax dynamic in style attribute.


# Javascript event callback #
Rabbit provider two kind of type of Callback Modifier
  1. ScriptInvokeModifier
  1. AjaxInvokeModifier

**ScriptInvokeModifier** use ScriptInvokeCallback class to generate script code and write the code into attribute you want.
When the broswer event is triggered, the URL will be redirected to trigger the program you assign in the server side.

**AjaxInvokeModifier** use AjaxInvokeCallback class to generate script code and write the code into attribute you want.
When the broswer event is triggered, the Javascript will execute AJAX program and trigger the program you assign in the server side.

```
package os.rabbit.demo;
import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.parser.Tag;


public class ScriptEventCallbackDemo extends Component {
	private Label behaviorLabel;
	private Component mouseArea;
	private AjaxInvokeModifier mouseOverModifier;
	private AjaxInvokeModifier mouseOutModifier;
	public ScriptEventCallbackDemo(Tag tag) {
		super(tag);
	}
	@Override
	protected void initial() {
		mouseOverModifier = new AjaxInvokeModifier(mouseArea, "onmouseover", new ITrigger() {
			
			@Override
			public void invoke() {
				behaviorLabel.setValue("mouse over");
				/**
				 * repaint() method is used to tell broswer which component need to rerender.
				 * Note: repaint() method is only worked on AJAX invoke.
				 */
				behaviorLabel.repaint();
			}
		});
		mouseOutModifier = new AjaxInvokeModifier(mouseArea, "onmouseout", new ITrigger() {
			
			@Override
			public void invoke() {
				behaviorLabel.setValue("mouse out");
				/**
				 * repaint() method is used to tell broswer which component need to rerender.
				 * Note: repaint() method is only worked on AJAX invoke.
				 */
				behaviorLabel.repaint();
			}
		});
	}
}
```
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Script Event Callback Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.ScriptEventCallbackDemo">
	<span style="color: #FF0000" rabbit:id="behaviorLabel"></span>
	<br />
	<div rabbit:id="mouseArea" style="width:200px; height:200px; background-color:#CCC">
		This is mouse area, when you move the mouse over or out here, the server callback method will be invoked.
	</div>
</body>
</html>

```
# Advanced Modifier #