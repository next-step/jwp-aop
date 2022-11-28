package core.aop.exception;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import core.annotation.ExceptionHandler;
import core.mvc.tobe.HandlerExecution;
import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.ExceptionArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.HttpResponseArgumentResolver;

public class ExceptionHandlerConverter {

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private final List<ArgumentResolver> argumentResolvers = List.of(
        new HttpRequestArgumentResolver(),
        new HttpResponseArgumentResolver(),
        new ExceptionArgumentResolver()
    );

    public Map<? extends Class<? extends Throwable>, ? extends HandlerExecution> convert(Object controllerAdvice) {
        Map<Class<? extends Throwable>, HandlerExecution> handlers = new HashMap<>();

        Method[] methods = controllerAdvice.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(ExceptionHandler.class)) {
                continue;
            }

            handlers.putAll(addExceptionHandlers(controllerAdvice, method));
        }

        return handlers;
    }

    private Map<Class<? extends Throwable>, ? extends HandlerExecution> addExceptionHandlers(Object target,
        Method method) {
        Class<? extends Throwable> key = method.getDeclaredAnnotation(ExceptionHandler.class).exception();

        var handlerExecution = new HandlerExecution(PARAMETER_NAME_DISCOVERER, argumentResolvers, target, method);

        return Map.of(key, handlerExecution);
    }
}
