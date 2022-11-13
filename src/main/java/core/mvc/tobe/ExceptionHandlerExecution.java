package core.mvc.tobe;


import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ExceptionHandlerExecution {

    private static final ArgumentResolvers ARGUMENT_RESOLVERS = ArgumentResolvers.getDefaultResolvers();

    private final Object target;
    private final Method method;

    public ExceptionHandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return (ModelAndView) method.invoke(target, ARGUMENT_RESOLVERS.resolve(method, request, response, throwable));
    }
}
