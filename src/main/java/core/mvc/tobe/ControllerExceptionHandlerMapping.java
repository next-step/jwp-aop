package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.di.context.ApplicationContext;
import core.mvc.ExceptionHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ControllerExceptionHandlerMapping implements ExceptionHandlerMapping {
    private ApplicationContext applicationContext;
    private ExceptionHandlerConverter exceptionHandlerConverter;
    private Map<Class<?>, HandlerExecution> exceptionHandlerExecutions = Maps.newHashMap();

    public ControllerExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    @Override
    public void initialize() {
        Map<Class<?>, Object> adviceHandlers = getControllers(applicationContext);
        exceptionHandlerExecutions.putAll(exceptionHandlerConverter.convert(adviceHandlers));
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext applicationContext) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : applicationContext.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, applicationContext.getBean(clazz));
            }
        }
        return controllers;
    }

    @Override
    public HandlerExecution getExceptionHandler(Throwable throwable, Object handler) {
        HandlerExecution handlerExecution = exceptionHandlerExecutions.get(throwable.getClass());
        if (handlerExecution != null && isSameController(handler, handlerExecution)) {
            return handlerExecution;
        }
        return null;
    }

    private boolean isSameController(Object handler, HandlerExecution handlerExecution) {
        return ((HandlerExecution) handler).getTargetClass() == handlerExecution.getTargetClass();
    }

}
