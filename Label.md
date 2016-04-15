# Label Component #

### Java ###
```
package os.rabbit.demo;

import os.rabbit.ITrigger;
import os.rabbit.components.Component;
import os.rabbit.components.Label;
import os.rabbit.components.form.TextBox;
import os.rabbit.modifiers.AjaxInvokeModifier;
import os.rabbit.parser.Tag;

public class LabelDemo extends Component {
	private Label label;

	
	public LabelDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {

	}

	@Override
	protected void beforeRender() {
		label.setValue(translate("Hello world !!"));

	}
	
}

```

---

### HTML ###
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Label Demo</title>
</head>

<body>
	<div rabbit:class="os.rabbit.demo.LabelDemo">
		<span rabbit:id="label">這裡的內容等一下會被取代成Hello world!!</span> 
	
	</div>

</body>
</html>

```