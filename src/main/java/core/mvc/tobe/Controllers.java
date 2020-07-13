package core.mvc.tobe;

import core.annotation.web.RequestMapping;
import core.mvc.tobe.support.ArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author KingCjy
 */
public class Controllers {

    private static final Logger logger = LoggerFactory.getLogger(Controllers.class);

    private Object[] controllers;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = new LinkedHashMap<>();
    private ArgumentResolver argumentResolver;

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public Controllers(Object[] controllers, ArgumentResolver argumentResolver) {
        this.controllers = controllers;
        this.argumentResolver = argumentResolver;

        for (Object target : this.controllers) {
            Class<?> controller = target.getClass();
            addHandlerExecution(target, controller.getMethods());
        }
    }

    private void addHandlerExecution(Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolver, target, method);
                    handlerExecutions.put(handlerKey, handlerExecution);
                    logger.info("Add - method: {}, path: {}, HandlerExecution: {}", requestMapping.method(), requestMapping.value(), method.getName());
                });
    }

    public Map<HandlerKey, HandlerExecution> getHandlerExecutions() {
        return handlerExecutions;
    }
}
