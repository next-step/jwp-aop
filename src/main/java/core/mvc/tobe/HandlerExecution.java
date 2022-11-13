package core.mvc.tobe;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

    private Object target;
    private Method method;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(Object target, Method method, ArgumentResolvers argumentResolvers) {
        this.target = target;
        this.method = method;
        this.argumentResolvers = argumentResolvers;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(target, argumentResolvers.resolve(method, request, response));
    }
}
