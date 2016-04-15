
```
package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.Label;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class ButtonDemo extends Component {
	private Label label;
	private TextBox input;
	private Button button;

	public ButtonDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		
		button.addUpdateComponent(input);
		button.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				label.setValue(input.getValue());
			}
		});
	}
	@Override
	protected void afterRender() {
		super.afterRender();
	}

}

```

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Button Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.ButtonDemo">
	<table border="0">
		<tr>
			<td><span rabbit:id="label">HelloWorld</span></td>
		</tr>
		<tr>
			<td><input type="text" rabbit:id="input" /></td>
		</tr>
		<tr>
			<td><input type="button" value="Click Me" rabbit:id="button" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</body>
</html>
```

You can also add confirmation message.
```
button.setConfirm("Are you sure you want to execute the click event?");
```