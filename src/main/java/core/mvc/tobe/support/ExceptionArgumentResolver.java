package core.mvc.tobe.support;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.MethodParameter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionArgumentResolver implements ArgumentResolver {

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        final boolean controllerAdvicePresent = methodParameter.getMethod().getDeclaringClass().isAnnotationPresent(ControllerAdvice.class);
        final boolean exceptionHandlerPresent = methodParameter.getMethod().isAnnotationPresent(ExceptionHandler.class);
        final boolean assignableFromType = Throwable.class.isAssignableFrom(methodParameter.getType());
        return controllerAdvicePresent && exceptionHandlerPresent && assignableFromType;
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter, final HttpServletRequest request, final HttpServletResponse response) {
        return request.getAttribute("exceptionHandler");
    }
}
