package core.mvc.tobe;

import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.HttpResponseArgumentResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("예외 핸들러 실행기")
class ExceptionHandlerExecutionTest {

    @Test
    @DisplayName("생성")
    void instance() {
        Assertions.assertThatNoException().isThrownBy(() -> ExceptionHandlerExecution.of(
                new ControllerAdviceTest(),
                ControllerAdviceTest.class.getMethod("handleException", Exception.class, HttpServletRequest.class, HttpServletResponse.class),
                List.of(new HttpRequestArgumentResolver(), new HttpResponseArgumentResolver())
        ));
    }

    @Test
    @DisplayName("실행")
    void handle() throws Exception {
        //given
        ExceptionHandlerExecution handleException = ExceptionHandlerExecution.of(
                new ControllerAdviceTest(),
                ControllerAdviceTest.class.getMethod("handleException", Exception.class, HttpServletRequest.class, HttpServletResponse.class),
                List.of(new HttpRequestArgumentResolver(), new HttpResponseArgumentResolver())
        );
        //when
        ModelAndView result = handleException.handle(new Exception(), new MockHttpServletRequest(), new MockHttpServletResponse());
        //then
        assertThat(result).isInstanceOf(ModelAndView.class);
    }

    private static class ControllerAdviceTest {

        @ExceptionHandler(value = Exception.class)
        public ModelAndView handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView();
        }
    }
}
