Rabbit/EL is used to replacing Label Component.
You can use Rabbit/EL to do access bean object property by EL expression.
```
package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.parser.Tag;

public class ELDemo extends Component {

	private ELComponent bean;
	private ELComponent label;

	public ELDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
	
		label.setValue("EL demo");
		DemoObject el = new DemoObject();
	
		el.setAge(31);
		el.setName("Teco Li");
		el.setSex("Male");
		bean.setValue(el);
	
	}

}
```

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EL Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.ELDemo">
${label}
<table border="0" rabbit:id="list">
  <tr>
    <td>Hello everyone! My name is ${bean.name}!!</td>
  </tr>
  <tr>
    <td>I am ${bean.age} years old.</td>
  </tr>
  <tr>
    <td>And I am a ${bean.sex}</td>
  </tr>
</table>
</body>
</html>

```