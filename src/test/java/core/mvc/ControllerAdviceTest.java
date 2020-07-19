package core.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.controller.UserSessionUtils;
import next.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ControllerAdviceTest {

    private static DispatcherServlet dispatcher;
    private  MockHttpServletRequest request;
    private  MockHttpServletResponse response;

    private final User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");

    @BeforeAll
    static void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        ExceptionHandlerConverter exceptionHandlerConverter = ac.getBean(ExceptionHandlerConverter.class);
        ExceptionHandlerMapping exceptionHandlerMapping = new ExceptionHandlerMapping(ac, exceptionHandlerConverter);
        exceptionHandlerMapping.initialize();
        dispatcher.setExceptionHandlerMapping(exceptionHandlerMapping);
    }

    @BeforeEach
    void setUpEach() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }


    @Test
    void not_login_when_user_list() throws Exception {
        request.setRequestURI("/users");
        request.setMethod("GET");

        dispatcher.service(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }
}
