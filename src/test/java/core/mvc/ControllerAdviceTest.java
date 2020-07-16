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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @DisplayName("@ControllerAdvice 기능 테스트")
    @ParameterizedTest(name = "{index} - requestURI={0}, targetClassName={1}")
    @CsvSource({
        "'/test/controllerAdvice/firstSampleException', 'core.di.factory.example.FirstSampleException'",
        "'/test/controllerAdvice/secondSampleException', 'core.di.factory.example.SecondSampleException'",
        "'/test/controllerAdvice/thirdSampleException', 'core.di.factory.example.ThirdSampleException'",
    })
    void exceptionHandlers(String requestURI, String targetClassName) throws Exception {
        Class<?> targetClass = Class.forName(targetClassName);
        assertExceptionHandler(requestURI, targetClass);
    }


    private void assertExceptionHandler(String requestURI, Class<?> targetClass) throws ServletException, IOException {
        request.setRequestURI(requestURI);
        request.setMethod("GET");
        response = new MockHttpServletResponse();

        dispatcher.service(request, response);

        String responseBody = response.getContentAsString();
        log.debug("response body : {}", responseBody);

        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(responseBody).isEqualTo("\"" + targetClass.getSimpleName() + "\"");
    }
}
