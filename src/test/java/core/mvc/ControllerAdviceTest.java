package core.mvc;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.FirstSampleException;
import core.di.factory.example.SecondSampleException;
import core.di.factory.example.ThirdSampleException;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.asis.RequestMapping;
import core.mvc.tobe.*;
import lombok.extern.slf4j.Slf4j;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ControllerAdviceTest {
    private static DispatcherServlet dispatcher;
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;

    @BeforeAll
    static void beforeAll() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        HandlerConverter handlerConverter = ac.getBean(HandlerConverter.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, handlerConverter);
        ahm.initialize();

        AnnotationExceptionHandlerMapping aehm = new AnnotationExceptionHandlerMapping(ac, ac.getBean(ExceptionHandlerConverter.class));
        aehm.initialize();

        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerMapping(new RequestMapping());
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcher.addHandlerAdapter(new ControllerHandlerAdapter());

        dispatcher.addExceptionHandlerMapping(aehm);
        dispatcher.addExceptionHandlerAdapter(new ExceptionHandlerExecutionHandlerAdapter());
    }

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void singleExceptionHandler() throws Exception {
        String requestURI = "/test/controllerAdvice/firstSampleException";
        Class<? extends Throwable> targetThrowable = FirstSampleException.class;

        assertExceptionHandler(requestURI, targetThrowable);
    }

    @Test
    void multipleExceptionHandlers() throws Exception {
        String requestURI = "/test/controllerAdvice/secondSampleException";
        Class<? extends Throwable> targetThrowable = SecondSampleException.class;

        assertExceptionHandler(requestURI, targetThrowable);
    }

    @Test
    void multipleExceptionHandlers2() throws Exception {
        String requestURI = "/test/controllerAdvice/thirdSampleException";
        Class<? extends Throwable> targetThrowable = ThirdSampleException.class;

        assertExceptionHandler(requestURI, targetThrowable);
    }

    private void assertExceptionHandler(String requestURI, Class<? extends Throwable> targetThrowable) throws ServletException, IOException {
        request.setRequestURI(requestURI);
        request.setMethod("GET");
        response = new MockHttpServletResponse();

        dispatcher.service(request, response);

        String responseBody = response.getContentAsString();
        log.debug("response body : {}", responseBody);

        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(responseBody).isEqualTo("\"" + targetThrowable.getSimpleName() + "\"");
    }
}
