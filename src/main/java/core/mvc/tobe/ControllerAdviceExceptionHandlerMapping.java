package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;

import java.util.Map;

public class ControllerAdviceExceptionHandlerMapping implements ExceptionHandlerMapping {

    private final Map<Class<?>, ExceptionHandlerExecution> handlers = Maps.newHashMap();

    public ControllerAdviceExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter converter) {
        Map<Class<?>, Object> controllers = applicationContext.getBeansAnnotatedWith(ControllerAdvice.class);
        handlers.putAll(converter.convert(controllers));
    }

    @Override
    public ExceptionHandlerExecution getExceptionHandler(Object key) {
        return handlers.keySet()
            .stream()
            .map(handlers::get)
            .filter(handler -> handler.supports(((Throwable) key).getClass()))
            .findFirst()
            .orElse(null);
    }
}
