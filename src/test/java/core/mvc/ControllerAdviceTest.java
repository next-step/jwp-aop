package core.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ControllerAdviceTest {

    private DispatcherServlet dispatcher;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void not_logined_search_profile() throws Exception {
    }


}
