package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class DefaultExceptionHandler implements ExceptionHandler {

    private Method method;

    public DefaultExceptionHandler(Method method) {
        this.method = method;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException {
        Object instance = method.getDeclaringClass().getConstructor().newInstance();

        return (ModelAndView) method.invoke(instance, request, response);
    }

}
