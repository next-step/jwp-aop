package core.mvc.tobe;

import core.mvc.ModelAndView;
import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public final class ExceptionHandlerExecution {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private final Object target;
    private final Method method;
    private final List<ArgumentResolver> argumentResolvers;

    private ExceptionHandlerExecution(Object target, Method method, List<ArgumentResolver> argumentResolvers) {
        Assert.notNull(target, "'target' must not be null");
        Assert.notNull(method, "'method' must not be null");
        Assert.notNull(argumentResolvers, "'argumentResolvers' must not be null");
        Assert.isTrue(isReturnedModelAndView(method), String.format("method(%s) must be returned modelAndView", method));
        this.target = target;
        this.method = method;
        this.argumentResolvers = Collections.unmodifiableList(argumentResolvers);
    }

    public static ExceptionHandlerExecution of(Object target, Method method, List<ArgumentResolver> argumentResolvers) {
        return new ExceptionHandlerExecution(target, method, argumentResolvers);
    }

    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(target, arguments(throwable, request, response));
    }

    private Object[] arguments(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        return IntStream.range(0, method.getParameterCount())
                .mapToObj(i -> new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]))
                .map(methodParameter -> resolveArgument(methodParameter, throwable, request, response))
                .toArray();
    }

    private Object resolveArgument(MethodParameter methodParameter, Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        if (Throwable.class.isAssignableFrom(methodParameter.getType())) {
            return throwable;
        }
        return argument(methodParameter, request, response);
    }

    private Object argument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return argumentResolvers.stream()
                .filter(resolver -> resolver.supports(methodParameter))
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No suitable resolver for argument: " + methodParameter));
    }


    private boolean isReturnedModelAndView(Method method) {
        return ModelAndView.class.isAssignableFrom(method.getReturnType());
    }
}
