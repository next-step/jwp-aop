package next.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.aop.exception.ControllerExceptionHandlerMapping;
import core.aop.exception.ExceptionHandlerConverter;
import core.aop.exception.ExceptionHandlerMapping;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.asis.RequestMapping;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import core.web.WebApplicationInitializer;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        HandlerConverter handlerConverter = ac.getBean(HandlerConverter.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, handlerConverter);
        ahm.initialize();

        var exceptionHandlerConverter = ac.getBean(ExceptionHandlerConverter.class);
        var exceptionHandlerMapping = new ExceptionHandlerMapping(ac, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();

        var controllerExceptionHandlerMapping = new ControllerExceptionHandlerMapping(ac, exceptionHandlerConverter);
        controllerExceptionHandlerMapping.initialize();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(ahm);
        dispatcherServlet.addHandlerMapping(new RequestMapping());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcherServlet.addExceptionHandlerMapping(exceptionHandlerMapping);
        dispatcherServlet.addExceptionHandlerMapping(controllerExceptionHandlerMapping);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}
