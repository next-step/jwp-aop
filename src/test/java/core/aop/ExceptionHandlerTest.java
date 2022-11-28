package core.aop;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.aop.exception.ExceptionHandlerConverter;
import core.aop.exception.ExceptionHandlerMapping;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;

public class ExceptionHandlerTest {
    private DispatcherServlet dispatcher;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcher.addExceptionHandlerMapping(new ExceptionHandlerMapping(ac, new ExceptionHandlerConverter()));
    }

    @DisplayName("세션없이 권한이 있는 페이지 접근시, 로그인페이지로 이동시킨다.")
    @Test
    void login_success() throws Exception {
        MockHttpServletRequest secondRequest = new MockHttpServletRequest();
        secondRequest.setRequestURI("/users");
        secondRequest.setMethod("GET");
        MockHttpServletResponse secondResponse = new MockHttpServletResponse();

        dispatcher.service(secondRequest, secondResponse);

        assertThat(secondResponse.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }
}