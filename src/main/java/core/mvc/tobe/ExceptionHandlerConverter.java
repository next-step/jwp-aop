package core.mvc.tobe;

import com.google.common.collect.Lists;
import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.support.ArgumentResolver;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

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

    public Map<Class<? extends Throwable>, HandlerExecution> convert(Object controllerAdvice) {
        Map<Class<? extends Throwable>, HandlerExecution> handlers = new HashMap<>();

        Method[] methods = controllerAdvice.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(ExceptionHandler.class)) {
                continue;
            }

            handlers.putAll(addExceptionHandlers(controllerAdvice, method));
        }
        return handlers;
    }

    private Map<Class<? extends Throwable>, HandlerExecution> addExceptionHandlers(
        final Object target, final Method method) {
        Map<Class<? extends Throwable>, HandlerExecution> handlers = new HashMap<>();

        Class<? extends Throwable>[] classes = method.getDeclaredAnnotation(ExceptionHandler.class).value();
        for (Class clazz : classes) {
            handlers.put(clazz, new HandlerExecution(nameDiscoverer, argumentResolvers, target, method));
        }
        return handlers;
    }

}
