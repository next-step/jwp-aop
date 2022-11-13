package core.mvc.tobe;

import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerExecutionTest {

    @Test
    @DisplayName("실행")
    void handle() throws Throwable {
        ExceptionHandlerExecution handleException = new ExceptionHandlerExecution(
                new ControllerAdviceTest(),
                ControllerAdviceTest.class.getMethod("handleException", Exception.class, HttpServletRequest.class, HttpServletResponse.class)
        );

        ModelAndView result = handleException.handle(new Exception(), new MockHttpServletRequest(), new MockHttpServletResponse());
        assertThat(result).isInstanceOf(ModelAndView.class);
    }

    private static class ControllerAdviceTest {

        @ExceptionHandler(value = Exception.class)
        public ModelAndView handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView();
        }
    }
}