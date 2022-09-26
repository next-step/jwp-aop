package core.mvc.tobe;

import com.google.common.collect.Lists;
import core.di.context.ApplicationContext;
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
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExceptionHandlerConverter implements HandlerConverter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerConverter.class);

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    @Override
    public void addArgumentResolver(ArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
    }

    @Override
    public Map<HandlerKey, HandlerExecution> convert(ApplicationContext ac) {
        Map<Class<?>, Object> exceptionHandlers = getExceptionHandlerResolver(ac);

        Map<HandlerKey, HandlerExecution> handlers = new HashMap<>();
        Set<Class<?>> exceptionHandlerClass = exceptionHandlers.keySet();
        for (Class<?> exceptionHandler : exceptionHandlerClass) {
            Object target = exceptionHandlers.get(exceptionHandler);
            addHandlerExecution(handlers, target, exceptionHandler.getMethods());
        }

        return handlers;
    }

    private Map<Class<?>, Object> getExceptionHandlerResolver(ApplicationContext ac) {
        return ac.getBeanClasses()
                .stream()
                .filter(this::supportedException)
                .collect(Collectors.toMap(Function.identity(), ac::getBean));
    }

    private boolean supportedException(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(ExceptionHandler.class));
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
