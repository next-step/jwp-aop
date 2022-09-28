package core.mvc.tobe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExceptionHandlerConverter {

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private final List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public Map<Class<?>, ExceptionHandlerExecution> convert(Map<Class<?>, Object> handlers) {
        Map<Class<?>, ExceptionHandlerExecution> exceptionHandlers = Maps.newHashMap();
        Set<Class<?>> handlerClasses = handlers.keySet();
        for (Class<?> handlerClass : handlerClasses) {
            Object target = handlers.get(handlerClass);
            addHandlerExecution(exceptionHandlers, target, handlerClass.getMethods());
        }
        return exceptionHandlers;
    }

    private void addHandlerExecution(Map<Class<?>, ExceptionHandlerExecution> exceptionHandlers, Object target, Method[] methods) {
        Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
            .forEach(method -> addExceptionHandler(exceptionHandlers, target, method));
    }

    private void addExceptionHandler(Map<Class<?>, ExceptionHandlerExecution> exceptionHandlers, Object target, Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        Class<? extends Throwable>[] exceptionClasses = exceptionHandler.value();
        for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
            ExceptionHandlerExecution handlerExecution = new ExceptionHandlerExecution(nameDiscoverer, argumentResolvers, target, method, exceptionClass);
            Class<?> key = getKey(target, exceptionClass);
            exceptionHandlers.put(key, handlerExecution);
        }
    }

    private Class<?> getKey(Object target, Class<? extends Throwable> exceptionClass) {
        Class<?> targetClass = target.getClass();
        if (targetClass.isAnnotationPresent(Controller.class)) {
            return targetClass;
        }
        return exceptionClass;
    }
}
