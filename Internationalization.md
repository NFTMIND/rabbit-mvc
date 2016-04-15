# Internationalization #
Rabbit which provider 3 kind of way to internationalize web application as following:

[1.Localized file name](#1.Localized_file_name.md)

[2.Invoke translate() method of Rabbit](#2.Invoke_translate()_method_of_Rabbit.md)

[3.Refer the Localization component in HTML of Rabbit](#3.Refer_the_Localization_component_in_HTML_of_Rabbit.md)


### 1.Localized file name ###

Create a html file of Rabbit as hello.rbt. Then maybe we need to create another hello\_for\_china.rbt for Chinese language.

After user enter our website, Rabbit can auto load different page file of Rabbit by user's locale.
It is easy to solve the question. your just add locale code on the end of file name.

for example:

if your default file name is "hello.rbt", then you just need to add another locale file as hello_[[localeCode](localeCode.md)].rbt._

if user is from China, it may be zh\_CN like hello\_zh\_CN.rbt

if user is from France, it may be fr like hello\_fr.rbt


### 2.Invoke translate() method of Rabbit ###

We can also invoke method of component like "translate()" to get value from XML resource.
We show you a example as following:
```
package os.rabbit.demo;

import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ELComponent;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class TranslatorDemo extends Component {
	private ELComponent result;
	private TextBox input;
	private Button btnTranslate;
	public TranslatorDemo(Tag tag) {
		super(tag);
	}
	
	@Override
	protected void initial() {
		btnTranslate.addUpdateComponent(input);
		btnTranslate.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				result.setValue(translate(input.getValue()));
			}
		});
	}

	
}
```
HTML
```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>

<body rabbit:class="os.rabbit.demo.TranslatorDemo">
	${result}
	<br />
	<input type="text" rabbit:id="input" />
	<input type="button" value="Translate" rabbit:id="btnTranslate" />
</body>
</html>
```

We have to pay attention to locale XML file as following:

**WEB-INF/languages/zh\_TW.xml**
```
<?xml version="1.0" encoding="utf-8" ?>
<language>
	<translation value="Hello">你好</translation>
</language>
```
**WEB-INF/languages/en.xml**
```
<?xml version="1.0" encoding="utf-8" ?>
<language>
	<translation value="Hello">Hello</translation>
</language>
```

Open your broswer and key in URL likes following:
```
http://localhost:8080/rabbit/demo/translator_demo.rbt?rbtLocale=zh_TW
```
And key in Hello in the TextBox and click the "Translate" button.
You will see the Chinese word "你好" to be shown.

### 3.Refer the Localization component in HTML of Rabbit ###
Now, we will demo how to internationalized by Localization component.
We take previous example to modify as following:

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Translator Demo</title>
</head>

<body rabbit:class="os.rabbit.demo.TranslatorDemo">
	<div rabbit:class="os.rabbit.components.Localization">Please enter the word that you want to be translated.</div>
	<br /> ${result}
	<br />
	<input type="text" rabbit:id="input" />
	<input type="button" value="Translate" rabbit:id="btnTranslate" />
</body>
</html>

```

**WEB-INF/languages/zh\_TW.xml**
```
<?xml version="1.0" encoding="utf-8" ?>
<language>
	<translation value="Hello">你好</translation>
	<translation value="Please enter the word that you want to be translated.">請輸入您想被翻譯的字</translation>
</language>
```
**WEB-INF/languages/en.xml**
```
<?xml version="1.0" encoding="utf-8" ?>
<language>
	<translation value="Hello">Hello</translation>
	<translation value="Please enter the word that you want to be translated.">Please enter the word that you want to be translated.</translation>
</language>
```

Open your broswer and key in URL likes following:
```
http://localhost:8080/rabbit/demo/translator_demo.rbt?rbtLocale=zh_TW
```
You see, the the sentence "Please enter the word that you want to be translated."  that was translated as Chinese "請輸入您想被翻譯的字".
If you want to back in English, you can also key in URL as following:
```
http://localhost:8080/rabbit/demo/translator_demo.rbt?rbtLocale=en
```

# Auto Translation #
If you don't want to create locale xml file, it's ok.

The Rabbit framework will help you.

How to?

To modify web.xml as following:
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
	version="2.4">

	<servlet>
		<servlet-name>Rabbit</servlet-name>
		<servlet-class>os.rabbit.RabbitServlet</servlet-class>

		<init-param>
			<param-name>auto-translation</param-name>
			<param-value>true</param-value>
		</init-param>
		
		<load-on-startup>0</load-on-startup>

	</servlet>

	<servlet-mapping>
		<servlet-name>Rabbit</servlet-name>
		<url-pattern>*.rbt</url-pattern>
	</servlet-mapping>

	<servlet-mapping>
		<servlet-name>Rabbit</servlet-name>
		<url-pattern>/rbt/*</url-pattern>
	</servlet-mapping>
</web-app>  

```

Did you see "auto-translation" within `<init-param>` tag?

Yes, when you add the init-param into web.xml and assign the value as "true".The Rabbit will send sentence to Google Translator and save the result of translation into locale xml file.

If you are not satisfied, don't worry. you can also modify locale xml  file from /WEB-INF/languages/[[locale](locale.md)].xml