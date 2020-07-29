package core.mvc;

import core.mvc.tobe.ArgumentMatcher;
import core.mvc.tobe.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class DefaultExceptionHandler implements ExceptionHandler {

    private Method method;
    private ArgumentMatcher argumentMatcher;

    public DefaultExceptionHandler(Method method, ArgumentMatcher argumentMatcher) {
        this.method = method;
        this.argumentMatcher = argumentMatcher;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException {
        MethodParameter[] methodParameters = argumentMatcher.getMethodParameters(method);
        Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = argumentMatcher.resolveArgument(methodParameters[i], request, response);
        }

        Object instance = method.getDeclaringClass().getConstructor().newInstance();
        return (ModelAndView) method.invoke(instance, arguments);
    }

}
