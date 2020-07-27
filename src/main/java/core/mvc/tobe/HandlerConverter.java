package core.mvc.tobe;

import core.annotation.web.RequestMapping;
import core.mvc.tobe.support.ArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerConverter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerConverter.class);

    private ArgumentMatcher argumentMatcher;

    public HandlerConverter(ArgumentMatcher argumentMatcher) {
        this.argumentMatcher = argumentMatcher;
    }

    public void addArgumentResolver(ArgumentResolver argumentResolver) {
        this.argumentMatcher.addArgumentResolver(argumentResolver);
    }

    public Map<HandlerKey, HandlerExecution> convert(Map<Class<?>, Object> controllers) {
        Map<HandlerKey, HandlerExecution> handlers = new HashMap<>();
        Set<Class<?>> controllerClazz = controllers.keySet();
        for (Class<?> controller : controllerClazz) {
            Object target = controllers.get(controller);
            addHandlerExecution(handlers, target, controller.getMethods());
        }

        return handlers;
    }

    private void addHandlerExecution(Map<HandlerKey, HandlerExecution> handlers, final Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution handlerExecution = new HandlerExecution(argumentMatcher, target, method);
                    handlers.put(handlerKey, handlerExecution);
                    logger.info("Add - method: {}, path: {}, HandlerExecution: {}", requestMapping.method(), requestMapping.value(), method.getName());
                });
    }

}
