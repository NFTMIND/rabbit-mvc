# Rabbit Callback Handler #
## Where does Trigger be used? ##
In the case, we need to explain Trigger before understand Callback.
What is Trigger?
What relation is it with Callback?

Trigger is provided mechanism by Rabbit for handle from remote invoke through HTTP protocal.

The Callback work based on Trigger mechanism.
So Trigger is very important in here. if you can understand the Trigger, maybe you could customize Callback by yourself.

We suppose you want the Java program to be invoked by remote. In the situation, perhaps Trigger is suitable for you.

We are now to demo how to use Trigger.
```
package os.rabbit.demo;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.parser.Tag;

public class TriggerDemo extends Component {

	public TriggerDemo(Tag tag) {
		super(tag);
	}

	@Override
	// note: register Trigger need to write it on initial() method. because registering Trigger can be only once.
	public void initial() {
		getPage().addTrigger("triggeMe", new ITrigger() {

			public void invoke() {
				// this area program will be executed.
			}
		});
	}

}
```
### HTML-trigger\_demo.rbt ###
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Trigger demo</title>
</head>

<body rabbit:class="os.rabbit.demo.TriggerDemo">
</body>
</html>

```
We jsut need to enter URL as following:
```
http://localhost:8080/rabbit/demo/trigger_demo.rbt?rbtType=INVOKE&triggerId=triggeMe
```
**rbtType**
Then the method of Trigger will be invoked.
Look at the **rbtType** which can be three kind of type as following:
  1. JAX\_INVOKE:When you use this way, Rabbit will render XML for Rabbit Javascript protocal, it is always used by Rabbit internal.
  1. NVOKE:When you use this way, Rabbit will render html code and response to you.
  1. NVOKE\_WITHOUT\_PAGE\_RENDER:When you use this way, Rabbit only invoke Trigger but without response.
**triggerId**
triggerId is mapped by your registered name while you invoke addTrigger() method.

You understood about the Trigger.

We are starting Callback of Rabbit.

## How does Callback work? ##
Rabbit Callback is a Javascript generator.
It's is used to generating callback script.
When the user's broswer execute the script, the server trigger will be invoked.

Rabbit have variant kind of Callback. We show you as following:
1.URLInvokeCallback
> It is used to generate callback URL.
2.ScriptInvokeCallback
> It can generate Javascript code which is used to redirect to new URL and invoke server Trigger
3.AjaxInvokeCallback
> It can generate Javascript code which is used to invoke server Trigger By AJAX.

We will give you a example to explorer more secret of Callback.
```
package os.rabbit.demo;

import java.io.PrintWriter;
import java.io.StringWriter;

import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.callbacks.ScriptInvokeCallback;
import os.rabbit.callbacks.URLInvokeCallback;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.components.IButtonListener;
import os.rabbit.parser.Tag;

public class CallbackDemo extends Component {
	private ELComponent code;
	private Button urlBtn;
	private Button scriptBtn;
	private Button ajaxBtn;

	public CallbackDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		final URLInvokeCallback urlInvokeCallback = new URLInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		final ScriptInvokeCallback scriptInvokeCallback = new ScriptInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		final AjaxInvokeCallback ajaxInvokeCallback = new AjaxInvokeCallback(getPage(), new ITrigger() {
			
			@Override
			public void invoke() {
				
			}
		});
		urlBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				urlInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
		scriptBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				scriptInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
		ajaxBtn.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				StringWriter writer = new StringWriter();
				PrintWriter pw = new PrintWriter(writer);
				ajaxInvokeCallback.render(pw);
				code.setValue(writer.toString());
			}
		});
	}

	@Override
	protected void beforeRender() {
		
	}

}
```
**HTML**
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Callback Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.CallbackDemo">
	We have three kind of button to show different render result of callback.
	<br /> generate 1:URLInvokeCallback
	<br /> generate 2:ScriptInvokeCallback
	<br /> generate 3:AjaxInvokeCallback
	<br />
	<br /> ${code}
	<br />
	<input type="button" value="URL" rabbit:id="urlBtn" />
	<input type="button" value="Script" rabbit:id="scriptBtn" />
	<input type="button" value="AJAX" rabbit:id="ajaxBtn" />

</body>
</html>
```

Result as following:

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_001.jpg'>

Click "URL" Button<br>
<br>
<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_002.jpg'>

Click "Script" Button<br>
<br>
<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_003.jpg'>

Click "AJAX" Button<br>
<br>
<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_004.jpg'>


I believe you already understood Callback function when you saw the screen of result.<br>
<br>
Next, we will let you understand how to apply callback in your web application.<br>
<br>
<br>
<h2>Apply Callback</h2>
Likewise, we will give you a example. Let you can understand Callback more deep. see the following:<br>
<br>
<pre><code>package os.rabbit.demo;<br>
<br>
import java.io.PrintWriter;<br>
<br>
import os.rabbit.IRender;<br>
import os.rabbit.ITrigger;<br>
import os.rabbit.callbacks.ScriptInvokeCallback;<br>
import os.rabbit.components.Component;<br>
import os.rabbit.components.Label;<br>
import os.rabbit.components.form.TextBox;<br>
import os.rabbit.modifiers.AttributeModifier;<br>
import os.rabbit.parser.Tag;<br>
<br>
public class CallbackApplyDemo extends Component {<br>
	private Label label;<br>
	private TextBox input;<br>
	private ScriptInvokeCallback callback;<br>
	public CallbackApplyDemo(Tag tag) {<br>
		super(tag);<br>
<br>
	}<br>
	<br>
	@Override<br>
	protected void initial() {<br>
		callback = new ScriptInvokeCallback(getPage(), new ITrigger() {<br>
			<br>
			@Override<br>
			public void invoke() {<br>
				label.setValue(input.getValue());<br>
			}<br>
		});<br>
		//if you added input component into callback, server side will get input value when callback method be triggered<br>
		callback.addUpdateComponent(input);<br>
		<br>
		getPage().addScript("callbackApplyDemo", new IRender() {<br>
			<br>
			@Override<br>
			public void render(PrintWriter writer) {<br>
				writer.println("function invokeRemoteMethod() {");<br>
			<br>
				callback.render(writer);<br>
				writer.println();<br>
				writer.println("}");<br>
				<br>
				<br>
			}<br>
		});<br>
		//The way is used to modify tag attribute<br>
		new AttributeModifier(input, "onblur", "invokeRemoteMethod()");<br>
	}<br>
	<br>
	@Override<br>
	protected void beforeRender() {<br>
		if(label.getValue() == null) {<br>
			label.setValue("empty");<br>
		}<br>
	}<br>
<br>
}<br>
</code></pre>

HTML<br>
<pre><code>&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;<br>
&lt;html xmlns="http://www.w3.org/1999/xhtml"&gt;<br>
&lt;head&gt;<br>
&lt;meta http-equiv="Content-Type" content="text/html; charset=utf-8" /&gt;<br>
&lt;title&gt;Callback Apply Demo&lt;/title&gt;<br>
&lt;/head&gt;<br>
<br>
&lt;body rabbit:class="os.rabbit.demo.CallbackApplyDemo"&gt;<br>
	Key in the text on the text box<br>
	&lt;br /&gt;<br>
	&lt;div&gt;<br>
		server receive value is &lt;span style="color: #FF0000" rabbit:id="label"&gt;&lt;/span&gt;<br>
	&lt;/div&gt;<br>
	&lt;input rabbit:id="input" /&gt;<br>
&lt;/body&gt;<br>
&lt;/html&gt;<br>
</code></pre>

Result as following:<br>
<br>
Initial screen<br>
<br>
<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_005.jpg'>

I typed text into text box.<br>
<br>
<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_006.jpg'>

This example show you how to use Callback to generate Script code and custom Script by yourself.<br>
We're now understand by the example. The Callback will often be used when you want to customize component.<br>
<br>
You can also trying use AjaxInvokeCallback to do samething.<br>
<br>
Do you clear understand about Callback?<br>
Very clear?<br>
Well done