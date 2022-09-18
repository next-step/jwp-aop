package core.mvc.tobe;

import core.mvc.HandlerMapping;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAdviceExceptionHandlerMapping implements HandlerMapping<Throwable> {

    private static final ExceptionHandlerConverter CONVERTER = ExceptionHandlerConverter.instance();

    private final List<Object> controllers;
    private final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions = new HashMap<>();

    private ControllerAdviceExceptionHandlerMapping(List<Object> controllers) {
        Assert.notNull(controllers, "'controllers' must not be null");
        this.controllers = controllers;
    }

    public static ControllerAdviceExceptionHandlerMapping of(List<Object> controllers) {
        return new ControllerAdviceExceptionHandlerMapping(controllers);
    }

    @Override
    public void initialize() {
        handlerExecutions.putAll(CONVERTER.convert(controllers));
    }

    @Override
    public Object getHandler(Throwable target) {
        return handlerExecutions.get(target.getClass());
    }
}
