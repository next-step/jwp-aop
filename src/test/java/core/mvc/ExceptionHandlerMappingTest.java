package core.mvc;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.security.RequiredLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("예외와 컨트롤러 어드바이스와 매칭하여 메소드를 실행 시키기 위한 클래스")
class ExceptionHandlerMappingTest {
    private ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);


    @BeforeEach
    void setup() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        handlerAdapterRegistry.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        exceptionHandlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    @Test
    @DisplayName("예외를 잘 처리하는지")
    void handle() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();


        assertThatCode(() ->
                handlerExecutor.handle(
                        request,
                        response,
                        exceptionHandlerMappingRegistry.getHandler(new NumberFormatException("hi")).get()
                )
        ).doesNotThrowAnyException();

        assertThatCode(() ->
                handlerExecutor.handle(
                        request,
                        response,
                        exceptionHandlerMappingRegistry.getHandler(new IllegalArgumentException("hi")).get()
                )
        ).doesNotThrowAnyException();

        ModelAndView mav = handlerExecutor.handle(
                request,
                response,
                exceptionHandlerMappingRegistry.getHandler(new RequiredLoginException("hi")).get()
        );

        mav.getView().render(new HashMap<>(), request, response);
        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }

    @Test
    @DisplayName("원하는 예외가 없을때 optional 반환")
    void noMatchingException() {
        assertThat(exceptionHandlerMappingRegistry.getHandler(new NullPointerException("hi"))).isEmpty();
    }
}