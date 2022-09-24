package core.mvc.tobe;

import java.util.Map;

import com.google.common.collect.Maps;

import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;

public class ExceptionHandlerMapping {

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public ExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter handlerConverter) {
        Map<Class<?>, Object> controllerAdvices = getControllerAdvices(applicationContext);
        handlerExecutions.putAll(handlerConverter.convert(controllerAdvices));
    }

    private Map<Class<?>, Object> getControllerAdvices(ApplicationContext applicationContext) {
        Map<Class<?>, Object> controllerAdvices = Maps.newHashMap();
        applicationContext.getBeanClasses()
            .stream()
            .filter(clazz -> clazz.isAnnotationPresent(ControllerAdvice.class))
            .forEach(clazz -> controllerAdvices.put(clazz, applicationContext.getBean(clazz)));
        return controllerAdvices;
    }

    public HandlerExecution getHandler(Throwable throwable) {
        return handlerExecutions.get(throwable.getClass());
    }
}
