package next.controller;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.tobe.*;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerAdviceTest {

    private DispatcherServlet dispatcher;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        ExceptionHandlerConverter exceptionHandlerConverter = ac.getBean(ExceptionHandlerConverter.class);
        ExceptionHandlerMappings exceptionHandlerMappings = new ExceptionHandlerMappings(ac, exceptionHandlerConverter);
        dispatcher.setExceptionHandlerMapping(exceptionHandlerMappings);
    }

    @DisplayName("@ControllerAdvice 의 @ExceptionHandler 로 예외를 처리한다.")
    @Test
    void loginRequired() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questions");
        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcher.service(request, response);

        assertThat(response.getHeader("message")).isNull();
        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }

    @DisplayName("@Controller, @ControllerAdvice 에 모두 @ExceptionHandler 가 존재하면, @Controller 의 @ExceptionHandler 가 우선적으로 동작한다.")
    @Test
    void handleByController() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/login-required");
        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcher.service(request, response);

        assertThat(response.getHeader("message")).isEqualTo("handle RequiredLoginException in controller");
        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }
}
