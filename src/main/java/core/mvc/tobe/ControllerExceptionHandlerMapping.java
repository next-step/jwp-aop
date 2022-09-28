package core.mvc.tobe;

import java.util.Map;

import com.google.common.collect.Maps;

import core.annotation.web.Controller;
import core.di.context.ApplicationContext;

public class ControllerExceptionHandlerMapping extends AbstractExceptionHandlerMapping {

    private final Map<Class<?>, HandlerExecution> handlers = Maps.newHashMap();

    public ControllerExceptionHandlerMapping(ApplicationContext applicationContext,
                                             ExceptionHandlerConverter converter) {
        Map<Class<?>, Object> controllers = applicationContext.getBeansAnnotatedWith(Controller.class);
        handlers.putAll(converter.convert(controllers));
    }

    @Override
    public HandlerExecution getExceptionHandler(Object handler, Throwable throwable) {
        HandlerExecution exceptionHandler = handlers.get(throwable.getClass());
        if (exceptionHandler != null && hasSameTargetClass(handler, exceptionHandler)) {
            return exceptionHandler;
        }
        return null;
    }

    private boolean hasSameTargetClass(Object handler, HandlerExecution exceptionHandler) {
        return ((HandlerExecution) handler).hasSameTarget(exceptionHandler);
    }
}
