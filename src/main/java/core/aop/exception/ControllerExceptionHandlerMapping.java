package core.aop.exception;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.web.Controller;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import core.mvc.tobe.HandlerExecution;

public class ControllerExceptionHandlerMapping implements HandlerMapping {

    private Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = Maps.newHashMap();

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter exceptionHandlerConverter;

    public ControllerExceptionHandlerMapping(ApplicationContext applicationContext,
        ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    @Override
    public void initialize() {
        var controllers = applicationContext.getBeanClasses()
            .stream()
            .filter(it -> it.isAnnotationPresent(Controller.class))
            .collect(Collectors.toSet());

        for (Class<?> controller : controllers) {
            Object controllerObj = applicationContext.getBean(controller);
            handlerExecutions.putAll(exceptionHandlerConverter.convert(controllerObj));
        }
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        Class<?> exceptionClass = request.getAttribute("exceptionHandle").getClass();
        return handlerExecutions.get(exceptionClass);
    }
}
