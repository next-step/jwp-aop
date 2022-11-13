package core.mvc.tobe.support;

import core.mvc.HandlerMapping;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.ExceptionHandlerExecution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAdviceExceptionHandlerMapping implements HandlerMapping<Throwable> {

    private static final ExceptionHandlerConverter CONVERTER = ExceptionHandlerConverter.getInstance();
    private final List<Object> controllers;
    private final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions = new HashMap<>();

    public ControllerAdviceExceptionHandlerMapping(List<Object> controllers) {
        this.controllers = controllers;
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
