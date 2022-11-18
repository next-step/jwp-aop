package next.config;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.asis.RequestMapping;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.ControllerExceptionHandlerMapping;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.ExceptionHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import core.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        HandlerConverter handlerConverter = ac.getBean(HandlerConverter.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, handlerConverter);
        ahm.initialize();

        final ExceptionHandlerConverter exceptionHandlerConverter = ac.getBean(ExceptionHandlerConverter.class);
        final ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(ac, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();

        final ControllerExceptionHandlerMapping controllerExceptionHandlerMapping = new ControllerExceptionHandlerMapping(ac, exceptionHandlerConverter);
        controllerExceptionHandlerMapping.initialize();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addExceptionHandlerMapping(controllerExceptionHandlerMapping);
        dispatcherServlet.addExceptionHandlerMapping(exceptionHandlerMapping);
        dispatcherServlet.addHandlerMapping(ahm);
        dispatcherServlet.addHandlerMapping(new RequestMapping());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}
