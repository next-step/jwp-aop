package core.mvc.tobe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.di.context.ApplicationContext;
import core.mvc.tobe.support.ArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestHandlerConverter implements HandlerConverter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerConverter.class);

    private final List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

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
        Map<Class<?>, Object> controllers = getController(ac);

        Map<HandlerKey, HandlerExecution> handlers = new HashMap<>();
        Set<Class<?>> controllerClazz = controllers.keySet();
        for (Class<?> controller : controllerClazz) {
            Object target = controllers.get(controller);
            addHandlerExecution(handlers, target, controller.getMethods());
        }

        return handlers;
    }

    private Map<Class<?>, Object> getController(ApplicationContext ac) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    private void addHandlerExecution(Map<HandlerKey, HandlerExecution> handlers, final Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    RequestHandlerKey requestHandlerKey = new RequestHandlerKey(requestMapping.value(), requestMapping.method());
                    HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolvers, target, method);
                    handlers.put(requestHandlerKey, handlerExecution);
                    logger.info("Add - method: {}, path: {}, HandlerExecution: {}", requestMapping.method(), requestMapping.value(), method.getName());
                });
    }

}
