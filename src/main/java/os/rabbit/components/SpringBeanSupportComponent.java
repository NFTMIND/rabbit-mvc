package os.rabbit.components;

import org.springframework.web.context.WebApplicationContext;

import os.rabbit.parser.Tag;

public class SpringBeanSupportComponent extends Component {

    public SpringBeanSupportComponent(Tag tag) {
        super(tag);
        addComponentListener(new IComponentListener() {

            @Override
            public void initial() {
                WebApplicationContext context = (WebApplicationContext) getPage().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
                // context.getAutowireCapableBeanFactory().autowireBeanProperties(SpringBeanSupportComponent.this,
                // AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
                context.getAutowireCapableBeanFactory().autowireBean(SpringBeanSupportComponent.this);
            }

            @Override
            public void beforeRender() {

            }

            @Override
            public void afterRender() {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterBuild() {
                // TODO Auto-generated method stub

            }
        });

    }

    public static WebApplicationContext getWebApplicationContext(WebPage page) {
        return (WebApplicationContext) page.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }

    public WebApplicationContext getWebApplicatioonContext() {
        return getWebApplicationContext(getPage());
    }

    public Object getBean(String beanName) {
        WebApplicationContext context = getWebApplicatioonContext();
        if (context == null)
            throw new RuntimeException("The Spring Application Context is not exist, You have to config Spring initialize in web.xml");
        return context.getBean(beanName);
    }

}
