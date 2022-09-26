package core.mvc.tobe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.web.Controller;
import core.annotation.web.RequestMethod;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private final List<HandlerConverter> handlerConverters = Lists.newArrayList();

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(ApplicationContext applicationContext, HandlerConverter... handlerConverters) {
        this.applicationContext = applicationContext;

        Collections.addAll(this.handlerConverters, handlerConverters);
    }

    public void initialize() {
        for (HandlerConverter handlerConverter : handlerConverters) {
            handlerExecutions.putAll(handlerConverter.convert(applicationContext));
        }

        logger.info("Initialized AnnotationHandlerMapping!");
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
        logger.debug("Throwable : {}", e.getMessage());
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
