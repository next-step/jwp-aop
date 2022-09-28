package core.mvc.tobe;

import java.lang.annotation.Annotation;
import java.util.Map;

import com.google.common.collect.Maps;

import core.annotation.web.Controller;
import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;

public class ExceptionHandlerMappings {

    private final Map<Class<? extends Throwable>, HandlerExecution> controllerAdviceExceptionHandlers = Maps.newHashMap();
    private final Map<Class<?>, HandlerExecution> controllerExceptionHandlers = Maps.newHashMap();

    public ExceptionHandlerMappings(ApplicationContext applicationContext, ExceptionHandlerConverter handlerConverter) {
        Map<Class<?>, Object> controllerAdvices = getHandlersAnnotatedWith(applicationContext, ControllerAdvice.class);
        controllerAdviceExceptionHandlers.putAll(handlerConverter.convert(controllerAdvices));
        Map<Class<?>, Object> controllers = getHandlersAnnotatedWith(applicationContext, Controller.class);
        controllerExceptionHandlers.putAll(handlerConverter.convert(controllers));
    }

    private Map<Class<?>, Object> getHandlersAnnotatedWith(ApplicationContext applicationContext, Class<? extends Annotation> annotationClass) {
        Map<Class<?>, Object> handlers = Maps.newHashMap();
        applicationContext.getBeanClasses()
            .stream()
            .filter(clazz -> clazz.isAnnotationPresent(annotationClass))
            .forEach(clazz -> handlers.put(clazz, applicationContext.getBean(clazz)));
        return handlers;
    }

    public HandlerExecution getControllerExceptionHandler(Class<?> controllerClass) {
        return controllerExceptionHandlers.get(controllerClass);
    }

    public HandlerExecution getControllerAdviceExceptionHandler(Class<? extends Throwable> exceptionClass) {
        return controllerAdviceExceptionHandlers.get(exceptionClass);
    }
}
