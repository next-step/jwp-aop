package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.annotation.web.ControllerAdvice;
import core.annotation.web.RequestMethod;
import core.di.context.ApplicationContext;
import core.mvc.ExceptionHandlerMapping;
import core.mvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.*;

public class AnnotationHandlerMapping implements HandlerMapping, ExceptionHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private ApplicationContext applicationContext;
    private HandlerConverter handlerConverter;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = new LinkedHashMap<>();
    private Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlerExecutions = new LinkedHashMap<>();

    public AnnotationHandlerMapping(ApplicationContext applicationContext, HandlerConverter handlerConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllers = getBeansAnnotatedWith(applicationContext, Controller.class);
        handlerExecutions.putAll(handlerConverter.convert(controllers));
        exceptionHandlerExecutions.putAll(
                handlerConverter.convertAdvices(getBeansAnnotatedWith(applicationContext, ControllerAdvice.class))
        );
        logger.info("Initialized AnnotationHandlerMapping!");
    }

    @Override
    public Object getHandler(Throwable exception) {
        return exceptionHandlerExecutions.get(exception.getClass());
    }

    private Map<Class<?>, Object> getBeansAnnotatedWith(ApplicationContext ac, Class<? extends Annotation> annotationType) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(annotationType);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
        return getHandlerInternal(new HandlerKey(requestUri, rm));
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
