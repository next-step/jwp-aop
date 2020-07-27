package core.mvc.tobe;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

    private ArgumentMatcher argumentMatcher;
    private Object target;
    private Method method;

    public HandlerExecution(ArgumentMatcher argumentMatcher, Object target, Method method) {
        this.argumentMatcher = argumentMatcher;
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MethodParameter[] methodParameters = argumentMatcher.getMethodParameters(method);
        Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = argumentMatcher.resolveArgument(methodParameters[i], request, response);
        }

        return (ModelAndView) method.invoke(target, arguments);
    }

}
