package core.mvc.tobe;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.support.ArgumentResolver;

public class ExceptionHandlerConverter {

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private final List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public Map<Class<? extends Throwable>, HandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = Maps.newHashMap();
        Set<Class<?>> controllerAdviceClasses = controllerAdvices.keySet();
        for (Class<?> controllerAdviceClass : controllerAdviceClasses) {
            Object target = controllerAdvices.get(controllerAdviceClass);
            addHandlerExecution(exceptionHandlers, target, controllerAdviceClass.getMethods());
        }
        return exceptionHandlers;
    }

    private void addHandlerExecution(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers, Object target, Method[] methods) {
        Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
            .forEach(method -> addExceptionHandler(exceptionHandlers, target, method));
    }

    private void addExceptionHandler(Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers, Object target, Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        Class<? extends Throwable>[] exceptions = exceptionHandler.value();
        for (Class<? extends Throwable> exception : exceptions) {
            HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolvers, target, method);
            exceptionHandlers.put(exception, handlerExecution);
        }
    }
}
