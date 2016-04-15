Hello everyone.
I am Rabbit project creator. My name is Teco. I'm from Taiwan and my English is not good.
But I still try to explain everything in English.
Perhaps you will find some wrong English or you don't understand.
Please give me a E-mail. I will as much as possible to improve. Thank you.


# What is Rabbit? #
Rabbit is a MVC framework based on Java. We are always used to developing web application on Strtus, Spring MVC.... Do any of these framework really help you to save time of development? Does and of these framework really take easy for communication with UI designer? I think it's not really.

Why? because you spend too much time to focus on the flow control of the URL. and you have also to write Java code in the html. when you add the code into html, it will be nightmare for designer. it's too terrible, right?

So how should you do? Use Rabbit right now.
[Click here to start learning](Index.md)


Rabbit 4.4.9 [Click here](http://www.apollo-chess.com.tw/rabbit/files/rabbit-4.4.9.jar) to download.

Rabbit 4.5.1 [Click here](http://www.apollo-chess.com.tw/rabbit/files/rabbit-4.5.1.jar) to download. (We are not going to support Rabbit Tiles, please chnage to [Apache Tiles](http://tiles.apache.org/).


Maven pom.xml setting

```
	<dependencies>
		....

		<dependency>
			<groupId>com.xbd.mvc</groupId>
			<artifactId>rabbit</artifactId>
			<version>4.5.1-SNAPSHOT</version>
		</dependency>
		....
	</dependencies>
	<repositories>
		<repository>
			<id>rabbit</id>
			<url>http://www.apollo-chess.com.tw/rabbit/repository</url>
		</repository>
	</repositories>
```