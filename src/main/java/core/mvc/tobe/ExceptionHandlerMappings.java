package core.mvc.tobe;

import core.annotation.web.Controller;
import core.di.context.ApplicationContext;

public class ExceptionHandlerMappings {

    private final ControllerExceptionHandlerMapping controllerExceptionMapping;
    private final ControllerAdviceExceptionHandlerMapping controllerAdviceExceptionMapping;

    public ExceptionHandlerMappings(ApplicationContext applicationContext, ExceptionHandlerConverter converter) {
        this.controllerExceptionMapping = new ControllerExceptionHandlerMapping(applicationContext, converter);
        this.controllerAdviceExceptionMapping = new ControllerAdviceExceptionHandlerMapping(applicationContext, converter);
    }

    public ExceptionHandlerExecution getExceptionHandler(Object handler, Throwable throwable) {
        ExceptionHandlerExecution exceptionHandler = null;

        if (isController(handler)) {
            exceptionHandler = controllerExceptionMapping.getExceptionHandler(handler);
        }

        if (exceptionHandler == null) {
            exceptionHandler = controllerAdviceExceptionMapping.getExceptionHandler(throwable);
        }

        return exceptionHandler;
    }

    private static boolean isController(Object handler) {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        return handlerExecution.getTargetClass().isAnnotationPresent(Controller.class);
    }
}
