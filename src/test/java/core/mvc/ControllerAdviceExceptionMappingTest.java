package core.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerAdviceExceptionMappingTest {

    @DisplayName("특정 package 를 기준으로 ControllerAdvice 를 읽어 ExceptionHandler 를 등록한다.")
    @Test
    void controllerAdvice() throws ReflectiveOperationException {
        /* given */
        ControllerAdviceExceptionMapping exceptionMapping = new ControllerAdviceExceptionMapping("core.mvc.advice");

        /* when */
        exceptionMapping.initialize();

        /* then */
        ExceptionHandler handler = exceptionMapping.getHandler(RuntimeException.class);
        ModelAndView modelAndView = handler.handle(new MockHttpServletRequest(), new MockHttpServletResponse());

        Object result = modelAndView.getObject("test");
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("testValue");
    }

}
