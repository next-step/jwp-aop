package core.mvc.tobe.support;

import core.mvc.ExceptionHandlerExecutor;
import core.mvc.tobe.MethodParameter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionArgumentResolver implements ArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return Throwable.class.isAssignableFrom(methodParameter.getType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return request.getAttribute(ExceptionHandlerExecutor.EXCEPTION_ATTRIBUTE_KEY);
    }
}
