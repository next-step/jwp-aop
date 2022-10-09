package next.config;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.tobe.AnnotationExceptionHandlerMapping;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import core.web.WebApplicationInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        HandlerConverter handlerConverter = ac.getBean(HandlerConverter.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, handlerConverter);
        ahm.initialize();
        AnnotationExceptionHandlerMapping aehm = new AnnotationExceptionHandlerMapping(ac);
        aehm.initialize();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        // handler mapping
        dispatcherServlet.addHandlerMapping(ahm);
        dispatcherServlet.addExceptionHandlerMapping(aehm);
        // handler adapter
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}
