# Configure web.xml of Web Context #
You have to modify web.xml as following:
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
        version="2.4">
 
        <welcome-file-list>
            <welcome-file>index.rbt</welcome-file>
        </welcome-file-list>
        <servlet>
            <servlet-name>Rabbit</servlet-name>
            <servlet-class>os.rabbit.RabbitServlet</servlet-class>
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
if you are used Maven to developing web application. modify your pom.xml as following:
```
   <dependencies>
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>1.2.17</version>
            </dependency>
            <dependency>
                <groupId>com.googlecode.json-simple</groupId>
                <artifactId>json-simple</artifactId>
                <version>1.1.1</version>
            </dependency>
 
            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>servlet-api</artifactId>
                <version>2.5</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.tomcat</groupId>
                <artifactId>tomcat-catalina</artifactId>
                <version>7.0.27</version>
                <scope>provided</scope>
            </dependency>
 
            <dependency>
                <groupId>org.apache.httpcomponents</groupId>
                <artifactId>httpclient</artifactId>
                <version>4.3.2</version>
            </dependency>
 
            <dependency>
                <groupId>commons-fileupload</groupId>
                <artifactId>commons-fileupload</artifactId>
                <version>1.3</version>
            </dependency>
 
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>3.2.6.RELEASE</version>
            </dependency>
 
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-web</artifactId>
                <version>4.0.0.RELEASE</version>
            </dependency>
 
   </dependencies>
```

If you are not used to Maven, you have to add library into WEB-INF/lib as following:
```
log4j-1.2.17.jar
json-simple-1.1.1.jar
httpclient-4.3.2.jar
httpcore-4.3.1.jar
commons-logging-1.1.3.jar
commons-codec-1.6.jar
commons-fileupload-1.3.jar
commons-io-2.2.jar
spring-context-3.2.6.RELEASE.jar
spring-aop-3.2.6.RELEASE.jar
spring-beans-3.2.6.RELEASE.jar
spring-core-3.2.6.RELEASE.jar
spring-expression-3.2.6.RELEASE.jar
spring-web-4.0.0.RELEASE.jar
aopalliance-1.0.jar
commons-beanutils-1.9.1.jar
```