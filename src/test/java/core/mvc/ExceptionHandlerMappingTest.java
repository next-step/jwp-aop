package core.mvc;

import core.annotation.Configuration;
import core.annotation.web.*;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.tobe.AbstractNewController;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.model.User;
import next.security.LoginUser;
import next.security.RequiredLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@Configuration
public class ExceptionHandlerMappingTest {

    private DispatcherServlet dispatcher;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(this.getClass(), MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        ahm.initialize();

        final ExceptionHandlerMapping ehm = new ExceptionHandlerMapping(ac);
        ehm.initialize();

        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerMapping(new core.mvc.asis.RequestMapping());
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcher.addHandlerAdapter(new ControllerHandlerAdapter());
        dispatcher.addExceptionHandlerMapping(ehm);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @DisplayName("익셉션 핸들러 동작 테스트")
    @Test
    void exception_handler() throws Exception {
        request.setRequestURI("/api/exception");
        request.setMethod("GET");

        dispatcher.service(request, response);
        final String redirectedUrl = response.getRedirectedUrl();
        assertThat(redirectedUrl).isEqualTo("/");
    }

    @Controller
    public static class DummyController extends AbstractNewController {
        public DummyController() {
        }

        @RequestMapping(value = "/api/exception", method = RequestMethod.GET)
        public ModelAndView exception(@LoginUser User loginUser) throws Exception {
            return jspView("redirect:/abc");
        }
    }

    @ControllerAdvice
    public static class DummyControllerAdvice {
        public DummyControllerAdvice() {
        }

        @ExceptionHandler(RequiredLoginException.class)
        public ModelAndView requiredLoginExceptionHandler() {
            return new ModelAndView(new JspView("redirect:/"));
        }
    }
}