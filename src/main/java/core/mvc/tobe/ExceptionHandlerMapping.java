package core.mvc.tobe;

import java.lang.annotation.Annotation;
import java.util.Map;

import com.google.common.collect.Maps;

import core.annotation.web.Controller;
import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;

public class ExceptionHandlerMapping {

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public ExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter handlerConverter) {
        Map<Class<?>, Object> controllerAdvices = getHandlersAnnotatedWith(applicationContext, ControllerAdvice.class);
        Map<Class<?>, Object> controllers = getHandlersAnnotatedWith(applicationContext, Controller.class);
        handlerExecutions.putAll(handlerConverter.convert(controllerAdvices));
        handlerExecutions.putAll(handlerConverter.convert(controllers));
    }

    private Map<Class<?>, Object> getHandlersAnnotatedWith(ApplicationContext applicationContext, Class<? extends Annotation> annotationClass) {
        Map<Class<?>, Object> handlers = Maps.newHashMap();
        applicationContext.getBeanClasses()
            .stream()
            .filter(clazz -> clazz.isAnnotationPresent(annotationClass))
            .forEach(clazz -> handlers.put(clazz, applicationContext.getBean(clazz)));
        return handlers;
    }

    public HandlerExecution getHandler(Class<? extends Throwable> exceptionClass) {
        return handlerExecutions.get(exceptionClass);
    }
}
