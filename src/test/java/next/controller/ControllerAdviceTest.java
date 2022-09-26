package next.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.ExceptionHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;

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
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(ac, exceptionHandlerConverter);
        dispatcher.setExceptionHandlerMapping(exceptionHandlerMapping);
    }

    @DisplayName("RequiredLoginException 예외가 발생하면 로그인 페이지로 이동한다.")
    @Test
    void loginRequired() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questions");
        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcher.service(request, response);

        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }
}
