package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.di.context.ApplicationContext;

import java.util.Map;

public class ControllerExceptionHandlerMapping implements ExceptionHandlerMapping {

    private final Map<Class<?>, ExceptionHandlerExecution> handlers = Maps.newHashMap();

    public ControllerExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter converter) {
        Map<Class<?>, Object> controllers = applicationContext.getBeansAnnotatedWith(Controller.class);
        handlers.putAll(converter.convert(controllers));
    }

    @Override
    public ExceptionHandlerExecution getExceptionHandler(Object key) {
        HandlerExecution handler = (HandlerExecution) key;
        return handlers.get(handler.getTargetClass());
    }
}
