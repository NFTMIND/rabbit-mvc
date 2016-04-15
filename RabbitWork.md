When the rabbit page is loaded, Rabbit Servlet will create Object to handle the page by html tag attribute "rabbit:class". for example:
if your html code is as following:

```
<div class="idv.teco.HandleMe">
Hi
</div>
```
The "idv.teco.HandleMe" will be created to handle the area of web page.


You have to make sure your Java program extended os.rabbit.components.Component like this as following:
```
public class HandleMe extends os.rabbit.components.Component {
}
```



So you don't need worry about performance of Rabbit, because each WebPage are slowly only first created.

Rabbit is HTML generator actually. so we need to understand the work flow of Component of Rabbit.

We have two important method
  1. initial()
  1. beforeRender()

### initial() ###
Rabbit create Webpage only once. So when the page is created, Each initial() method of component will be invoked.

### beforeRender() ###
Rabbit need to generate HTML code and send to client.
So the beforeRender() method will be invoked before generate HTML.
That represent we need to fill value within this method.

---

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/rabbit_sd.png' width='700' />

---

We can understand Rabbit from the Sequence Diagram.
WebPage instance only be created once. Every lifecycle of web page are managed by Page Pool.

The Rabbit Servlet code must be like this:
```

WebPage page = pool.getPage(url);
String htmlCode = page.render();
```


---

<img src='http://www.apollo-chess.com.tw/rabbit/wiki_images/rabbit_cd.png' width='700' />

---

This is rabbit class diagram.
Did you find something?

Yes, Rabbit is composed by Modifier.
We take the easy example to explain how Rabbit work.
This is HTML code:
```
<html>
<body>
<span rabbit:id=”demoComponent”>
<span>content text</span>
</span>
</body>
</html>
```
Rabbit will separate part of three,
Part 1
```
<html>
<body>
```
Part 2
```
<span rabbit:id=”demoComponent”>
<span>content text</span>
</span>
```
Part 3
```
</body>
</html>
```
When you add **rabbit:id** attribute into the tag, the Tag will transform HTML section into a component. By default, Rabbit always create os.rabbit.Component class to map the Tag, you can also customize you own component through the **rabbit:class** attribute.
Every component have their modifier table.
Every modifier is stored range of start and end position.
The Rabbit internal will render HTML by modifier table.

I think perhaps you not understand, i will show you a simple example:
```
Hello!! This is rabbit test word.
```
if i want to modify "This" as "That".
I have to create a Modifier and assign you want to be changed range.
```
String text = ...;
Modifier myCustomizedModifier = new Modifier(text, 8, 12) {
	public void render(PrintWriter writer){
		writer.print("That");
	}
};
component.addModifier(modifier);
component.render(); // Result: Hello!! That is rabbit test word.
```
In rabbit internal, "Hello!! This is rabbit test word." will be separated part of three.
Part 1: TextModifier

```
"Hello!! "
```

Part 2: Customized Modifier

```
myCustomizedModifier
```

Part 3: TextModifier

```
" is rabbit test word."
```


Component internal:
```
StringWriter writer = new StringWriter();
PrintWriter pw = new PrintWriter(writer);
for(IModifier modifier : modifiers) {
modifier.render(pw);
}
// writer result as : "Hello!! That is rabbit test word."
```

I took as possible as easy way to show you the Rabbit how to work.
Hope you can get it.