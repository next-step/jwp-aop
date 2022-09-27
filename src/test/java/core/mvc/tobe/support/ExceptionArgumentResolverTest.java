package core.mvc.tobe.support;

import static org.assertj.core.api.Assertions.assertThat;

import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.MethodParameter;
import core.mvc.tobe.mock.MockControllerAdvice;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import next.security.RequiredLoginException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class ExceptionArgumentResolverTest {

    private final ExceptionArgumentResolver exceptionArgumentResolver = new ExceptionArgumentResolver();

    @DisplayName("ExceptionHandler 애너테이션이 적용된 메서드를 지원한다")
    @Test
    void supportExceptionHandlerAnnotation() throws NoSuchMethodException {
        final Method method = MockControllerAdvice.class.getDeclaredMethod("requiredLogin", RequiredLoginException.class);
        final ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        final MethodParameter methodParameter = new MethodParameter(method, RequiredLoginException.class, new Annotation[]{exceptionHandler},
            "exception");

        final boolean actual = exceptionArgumentResolver.supports(methodParameter);

        assertThat(actual).isTrue();
    }

    @DisplayName("ExceptionHandler 애너테이션이 적용되지 않은 메서드는 지원하지 않는다")
    @Test
    void unSupportExceptionHandlerAnnotation() throws NoSuchMethodException {
        final Method method = MockUser.class.getDeclaredMethod("getName");
        final MethodParameter methodParameter = new MethodParameter(method, MockUser.class, new Annotation[]{}, "");

        final boolean actual = exceptionArgumentResolver.supports(methodParameter);

        assertThat(actual).isFalse();
    }

    @DisplayName("ExceptionHandler 애너테이션이 적용된 메서드의 인자의 인스턴스를 반환한다")
    @Test
    void resolveArgument() throws NoSuchMethodException {
        //given
        final Method method = MockControllerAdvice.class.getDeclaredMethod("requiredLogin", RequiredLoginException.class);
        final ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        final MethodParameter methodParameter = new MethodParameter(method, RequiredLoginException.class, new Annotation[]{exceptionHandler},
            "exception");

        final RequiredLoginException requiredLoginException = new RequiredLoginException("로그인이 필요합니다.");

        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("exceptionHandler", requiredLoginException);
        final MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        final Object actual = exceptionArgumentResolver.resolveArgument(methodParameter, request, response);

        //then
        assertThat(actual).isEqualTo(requiredLoginException);
    }
}
