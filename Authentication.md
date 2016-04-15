# Authentication #
Rabbit provider simple authentication mechanism in every WebPage.

When Request start, Rabbit will take WebPage from Page Pool and invoke render() of WebPage.

Before render() is invoked, Rabbit will invoke WebPage.isAuthorized() for check whether the WebPage was authorized.

So if you can easy register **authorization validator** in WebPage. When WebPage.isAuthorized() is invoked, WebPage will execute **authorization validator table** to check the WebPage is safely accessible.

Now, you have understood. We will show a example for you as following:

```
package os.rabbit.demo;

import os.rabbit.components.Component;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.WebPage;
import os.rabbit.parser.Tag;

public class AuthorizationDemo extends Component {
	public AuthorizationDemo(Tag tag) {
		super(tag);
	}

	@Override
	protected void initial() {

		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			
			@Override
			public boolean validate(WebPage page) {
				//If authorized value have been existing in session, return true, otherwise false.
				Boolean authorized = (Boolean)getPage().getRequest().getSession().getAttribute("authorized");
				return authorized != null && authorized;
			}
		});
	}
}
```

Result:

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/screen_009.jpg' />

Of course, you can also assign handle page to handle the status. You just need modify web.xml:
```

	<servlet>
		<servlet-name>Rabbit</servlet-name>
		<servlet-class>os.rabbit.RabbitServlet</servlet-class>
		<init-param>
			<param-name>unauthorized</param-name>
			<param-value>login.rbt</param-value>
		</init-param>
		<load-on-startup>0</load-on-startup>

	</servlet>

```

If 401 error code throw, Rabbit will forward to **login.rbt**.