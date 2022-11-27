package core.aop.exception;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.ControllerAdvice;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import core.mvc.tobe.HandlerExecution;

public class ExceptionHandlerMapping implements HandlerMapping {

    private Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = Maps.newHashMap();

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter exceptionHandlerConverter;

    public ExceptionHandlerMapping(ApplicationContext applicationContext,
        ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    @Override
    public void initialize() {
        var controllerAdvices = applicationContext.getBeanClasses()
            .stream()
            .filter(it -> it.isAnnotationPresent(ControllerAdvice.class))
            .collect(Collectors.toSet());

        for (Class<?> controllerAdvice : controllerAdvices) {
            Object obj = applicationContext.getBean(controllerAdvice);
            handlerExecutions.putAll(exceptionHandlerConverter.convert(obj));
        }
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        Class<?> exceptionClass = request.getAttribute("exceptionHandle").getClass();
        return handlerExecutions.get(exceptionClass);
    }
}
