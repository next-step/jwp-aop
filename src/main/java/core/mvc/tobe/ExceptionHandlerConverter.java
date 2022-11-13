package core.mvc.tobe;

import com.google.common.collect.Lists;
import core.annotation.ExceptionHandler;
import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionHandlerConverter {
    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public Map<Class<?>, HandlerExecution> convert(Map<Class<?>, Object> exceptionHandlers) {
        Map<Class<?>, HandlerExecution> handlerExecutions = new HashMap<>();
        for (Class<?> exceptionHandlerClass : exceptionHandlers.keySet()) {
            Object target = exceptionHandlers.get(exceptionHandlerClass);
            addHandlerExecutions(handlerExecutions, target, exceptionHandlerClass.getMethods());
        }
        return handlerExecutions;
    }

    private void addHandlerExecutions(Map<Class<?>, HandlerExecution> handlerExecutions, Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> addHandlerExecutions(handlerExecutions, target, method));
    }

    private void addHandlerExecutions(Map<Class<?>, HandlerExecution> handlerExecutions, Object target, Method method) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        Class<? extends Throwable>[] exceptionClasses = exceptionHandler.value();
        for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
            HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolvers, target, method);
            handlerExecutions.put(exceptionClass, handlerExecution);
        }
    }
}
