package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.annotation.web.RequestMethod;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private ApplicationContext applicationContext;
    private HandlerConverter handlerConverter;
    private ExceptionHandlerConverter exceptionHandlerConverter;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(ApplicationContext applicationContext, HandlerConverter handlerConverter,
                                    ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = getControllers(applicationContext);
        handlerExecutions.putAll(handlerConverter.convert(controllers));

        Map<Class<?>, Object> exceptionHandleResolvers = getExceptionHandlerResolver(applicationContext);
        handlerExecutions.putAll(exceptionHandlerConverter.convert(exceptionHandleResolvers));
        logger.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    private Map<Class<?>, Object> getExceptionHandlerResolver(ApplicationContext ac) {
        return ac.getBeanClasses()
                .stream()
                .filter(this::supportedException)
                .collect(Collectors.toMap(Function.identity(), ac::getBean));
    }

    private boolean supportedException(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ControllerAdvice.class)) {
            return true;
        }

        return Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(ExceptionHandler.class));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
        return getHandlerInternal(new RequestHandlerKey(requestUri, rm));
    }

    @Override
    public Object getHandler(Throwable e) {
        logger.debug("Throwable : {}", e);
        return getHandlerInternal(new ExceptionHandlerKey(new Class[]{e.getClass()}));
    }

    private HandlerExecution getHandlerInternal(HandlerKey requestHandlerKey) {
        for (HandlerKey handlerKey : handlerExecutions.keySet()) {
            if (handlerKey.isMatch(requestHandlerKey)) {
                return handlerExecutions.get(handlerKey);
            }
        }

        return null;
    }
}
