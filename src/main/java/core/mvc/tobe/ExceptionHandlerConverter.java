package core.mvc.tobe;

import com.google.common.collect.Lists;
import core.mvc.tobe.support.ArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExceptionHandlerConverter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerConverter.class);

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public void addArgumentResolver(ArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
    }

    public Map<HandlerKey, HandlerExecution> convert(Map<Class<?>, Object> exceptionHandlers) {
        Map<HandlerKey, HandlerExecution> handlers = new HashMap<>();
        Set<Class<?>> exceptionHandlerClass = exceptionHandlers.keySet();
        for (Class<?> exceptionHandler : exceptionHandlerClass) {
            Object target = exceptionHandlers.get(exceptionHandler);
            addHandlerExecution(handlers, target, exceptionHandler.getMethods());
        }

        return handlers;
    }

    private void addHandlerExecution(Map<HandlerKey, HandlerExecution> handlers, final Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> {
                    ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
                    ExceptionHandlerKey exceptionHandlerKey = new ExceptionHandlerKey(exceptionHandler.value());
                    HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolvers, target, method);
                    handlers.put(exceptionHandlerKey, handlerExecution);
                    logger.info("Add - exception: {}, HandlerExecution: {}", exceptionHandler.value(), method.getName());
                });
    }
}
