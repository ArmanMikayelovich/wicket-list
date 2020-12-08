package com.mikayelovich;

import com.mikayelovich.pages.HomePage;
import com.mikayelovich.session.CustomSession;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        // add your configuration here

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        //Scan package for annotated beans
        ctx.scan("com.mikayelovich");
        ctx.refresh();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new CustomSession(request);
    }


}
