package core.mvc.tobe;

import core.mvc.ModelAndView;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public final class ExceptionHandlerExecution {

    private static final ArgumentResolvers ARGUMENT_RESOLVERS = ArgumentResolvers.defaultResolvers();

    private final Object target;
    private final Method method;

    private ExceptionHandlerExecution(Object target, Method method) {
        Assert.notNull(target, "'target' must not be null");
        Assert.notNull(method, "'method' must not be null");
        Assert.isTrue(isReturnedModelAndView(method), String.format("method(%s) must be returned modelAndView", method));
        this.target = target;
        this.method = method;
    }

    public static ExceptionHandlerExecution of(Object target, Method method) {
        return new ExceptionHandlerExecution(target, method);
    }

    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(target, ARGUMENT_RESOLVERS.resolve(method, request, response, throwable));
    }

    private boolean isReturnedModelAndView(Method method) {
        return ModelAndView.class.isAssignableFrom(method.getReturnType());
    }
}
