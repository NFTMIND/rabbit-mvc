# ListBuffer #
We always need loop in web application.
This demo how to write loop program in Rabbit as following:
```
package os.rabbit.demo;

import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.parser.Tag;

public class ListBufferDemo extends SpringBeanSupportComponent {

	private ListBuffer listBuffer;
	private Label lblNumber;

	public ListBufferDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {
		listBuffer.setEmptyDataMessage("No data");
	}

	@Override
	protected void beforeRender() {
		for (int loop = 0; loop < 100; loop++) {
			lblNumber.setValue(loop);
			listBuffer.flush();
		}
	}
}
```
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ListBuffer Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.ListBufferDemo">
<table border="1">
  <tr rabbit:id="listBuffer">
    <td rabbit:id="lblNumber">Number</td>
  </tr>
</table>
</body>
</html>
```