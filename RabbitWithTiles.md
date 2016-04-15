# Rabbit with Apache Tiles #
Rabbit project can be easy integrated with Apache Tiles.

Modify web.xml of web app.
```
	<listener>
	    <listener-class>
	        org.apache.tiles.extras.complete.CompleteAutoloadTilesListener
	    </listener-class>
	</listener>
	
	<servlet>
		<servlet-name>Tiles Dispatch Servlet</servlet-name>
		<servlet-class>org.apache.tiles.web.util.TilesDispatchServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>Tiles Dispatch Servlet</servlet-name>
		<url-pattern>*.tiles</url-pattern>
	</servlet-mapping>
```
And perhaps you need Tiles library if you have Maven in your Project.
```
		<dependency>
			<groupId>org.apache.tiles</groupId>
			<artifactId>tiles-extras</artifactId>
			<version>3.0.1</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.5.8</version>
		</dependency>
```
We are not going to provider more detail. If you want more detial, [click here](http://tiles.apache.org/)

Rabbit provided InsertAttributeComponent component for integrate with Tiles.

We will show you how to use it as following:

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><span rabbit:class="os.rabbit.tiles.InsertAttributeComponent" rabbit:id="title" /></title>
</head>

<body rabbit:class="os.rabbit.demo.ApacheTilesDemo">
This is Tiles template page
<div style="background-color:#CCC">
<span rabbit:class="os.rabbit.tiles.InsertAttributeComponent" rabbit:id="body">Hello</span>
</div>
</body>
</html>
```

tiles.xml
```
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE tiles-definitions PUBLIC
       "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN"
       "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
<tiles-definitions>

	<definition name="demo/apache_tiles_demo" template="/demo/apache_tiles_demo.rbt">
		<put-attribute name="body" value="/demo/tiles1.rbt" />
		<put-attribute name="title" value="Apache Tiles Demo" />
	</definition>
</tiles-definitions>
```

In the case, you can see **title** and **body**.
If it corresponding value is path, the Apache Tiles will replace the content of path to the tag. Otherwise corresponding content will replace the tag