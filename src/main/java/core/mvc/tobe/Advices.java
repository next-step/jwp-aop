package core.mvc.tobe;

import core.annotation.web.ExceptionHandler;
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
public class Advices {
    private static final Logger logger = LoggerFactory.getLogger(Advices.class);

    private Object[] advices;
    private Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = new LinkedHashMap<>();
    private ArgumentResolver argumentResolver;

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public Advices(Object[] advices, ArgumentResolver argumentResolver) {
        this.advices = advices;
        this.argumentResolver = argumentResolver;

        for (Object target : this.advices) {
            Class<?> advice = target.getClass();
            addHandlerExecution(target, advice.getMethods());
        }
    }

    private void addHandlerExecution(Object target, Method[] methods) {
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> {
                    ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
                    Arrays.stream(exceptionHandler.value()).forEach(handlerKey -> {
                        HandlerExecution handlerExecution = new HandlerExecution(nameDiscoverer, argumentResolver, target, method);
                        handlerExecutions.put(handlerKey, handlerExecution);
                        logger.info("Add - exception: {}", handlerKey);
                    });

                });
    }

    public Map<Class<? extends Throwable>, HandlerExecution> getHandlerExecutions() {
        return handlerExecutions;
    }
}