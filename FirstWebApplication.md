We know that about Struts, Spring MVC framework which always need us focus on flow control of URL. It's not really good. I want you to develop web application easier.
When you use Rabbit, you don't need to take care of the flow control, because Rabbit suggest you making the same file name as Java. for example:
if your html name is "hello\_world.rbt" and your java file name should be named as "HelloWorld.java"
it make sure you will easier to identify relation between html and java

```

package os.rabbit.demo;
 
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.parser.Tag;
 
public class LabelDemo extends Component {
	private Label label;
	public LabelDemo(Tag tag) {
    		super(tag);
	}
 
	@Override
	protected void beforeRender() {
    		label.setValue("Hello world !!");
	}
	
}
```

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Label Demo</title>
</head>
 
<body rabbit:class="os.rabbit.demo.LabelDemo">
<span rabbit:id="label">This text will be replaced as 'Hello world!!'</span>
</body>
</html>
```