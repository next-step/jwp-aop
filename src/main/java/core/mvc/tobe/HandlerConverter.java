package core.mvc.tobe;

import com.google.common.collect.Lists;
import core.annotation.web.RequestMapping;
import core.mvc.tobe.support.ArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.*;

public class HandlerConverter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerConverter.class);

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public void setArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers.addAll(argumentResolvers);
    }

    public void addArgumentResolver(ArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
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
                    HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolvers, target, method);
                    handlers.put(handlerKey, handlerExecution);
                    logger.info("Add - method: {}, path: {}, HandlerExecution: {}", requestMapping.method(), requestMapping.value(), method.getName());
                });
    }

}
