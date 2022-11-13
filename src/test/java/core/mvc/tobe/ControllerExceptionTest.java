package core.mvc.tobe;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.MyTestConfiguration;
import core.mvc.DispatcherServlet;
import core.mvc.ExceptionHandlerMapping;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.asis.RequestMapping;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ControllerExceptionTest {
    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyTestConfiguration.class);

        HandlerConverter handlerConverter = ac.getBean(HandlerConverter.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, handlerConverter);
        ahm.initialize();

        ExceptionHandlerConverter exceptionHandlerConverter = ac.getBean(ExceptionHandlerConverter.class);
        ExceptionHandlerMapping controllerExceptionHandlerMapping = new ControllerExceptionHandlerMapping(ac, exceptionHandlerConverter);
        ExceptionHandlerMapping controllerAdviceExceptionHandlerMapping = new ControllerAdviceExceptionHandlerMapping(ac, exceptionHandlerConverter);
        controllerExceptionHandlerMapping.initialize();
        controllerAdviceExceptionHandlerMapping.initialize();

        dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(ahm);
        dispatcherServlet.addHandlerMapping(new RequestMapping());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcherServlet.addExceptionHandlerMapping(controllerExceptionHandlerMapping);
        dispatcherServlet.addExceptionHandlerMapping(controllerAdviceExceptionHandlerMapping);
    }

    @DisplayName("Controller 에 ExceptionHandler 애노테이션이 없다면 ControllerAdvice 의 Exception 에 대한 후처리를 진행한다.")
    @Test
    void globalControllerAdviceException() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questions/v1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request, response);

        assertThat(response.getRedirectedUrl()).isEqualTo("/user/login.jsp");
        assertThat(response.getHeader("message")).isEqualTo("Controller Advice Exception Test");
    }

    @DisplayName("Controller 에 ExceptionHandler 애노테이션이 있다면 해당 ExceptionHandler 의 Exception 에 대한 후처리를 진행한다. (ControllerAdvice 의 ExceptionHandler 는 무시된다.")
    @Test
    void localExceptionHandlerException() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questions/v2");
        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request, response);

        assertThat(response.getRedirectedUrl()).isEqualTo("/user/login.jsp");
        assertThat(response.getHeader("message")).isEqualTo("ExceptionHandler Test in Specific Controller");
    }
}
