# Rabbit with Spring #

Rabbit also can integrate with Spring.
but you have to configure Spring Web in you project.

1.Add below block xml section into your WEB-INF/web.xml
```
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/spring.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener
		</listener-class>
	</listener>
```

2.Make your Java project to extend SpringSupportBeanComponent like this:
```

import os.rabbit.components.SpringBeanSupportComponent;
public class MyCustomizeComponent extends SpringSupportBeanComponent {
	public MyCustomizeComponent(Tag tag) {
		super(tag);
	}
}
```

3.Now, you can get any bean you want from Spring.
```
import os.rabbit.components.SpringBeanSupportComponent;
public class MyCustomizeComponent extends SpringSupportBeanComponent {
	public MyCustomizeComponent(Tag tag) {
		super(tag);
	}
	public void beforeRender() {
		// Get the bean you want
		Object myBean = getBean("myBean");
	}

}

```