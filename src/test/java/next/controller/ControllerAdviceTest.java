package next.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.ModelAndView;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.ExceptionHandlerMapping;
import core.mvc.tobe.HandlerExecution;
import next.config.MyConfiguration;

class ControllerAdviceTest {

    private ExceptionHandlerMapping mapping;

    @BeforeEach
    void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        mapping = new ExceptionHandlerMapping(applicationContext, new ExceptionHandlerConverter());
    }

    @Test
    @DisplayName("@ControllerAdvice, @ExceptionHandler 어노테이션을 이용하여 예외를 처리한다.")
    void controllerAdvice() throws Exception {
        HandlerExecution exceptionHandler = mapping.getHandler(IllegalStateException.class);
        ModelAndView mav = exceptionHandler.handle(new MockHttpServletRequest(), new MockHttpServletResponse());
        assertThat(mav.getObject("exception")).isEqualTo("IllegalStateException in ControllerAdvice");
    }

    @Test
    @DisplayName("@ExceptionHandler 메서드가 @Controller, @ControllerAdvice 둘 다 존재한다면, @Controller 의 @ExceptionHandler 가 우선순위를 갖는다.")
    void controller() throws Exception {
        HandlerExecution exceptionHandler = mapping.getHandler(IllegalArgumentException.class);
        ModelAndView mav = exceptionHandler.handle(new MockHttpServletRequest(), new MockHttpServletResponse());
        assertThat(mav.getObject("exception")).isEqualTo("IllegalArgumentException in Controller");
    }
}
