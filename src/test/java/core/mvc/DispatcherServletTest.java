package core.mvc;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.ConnectionHolder;
import core.jdbc.ConnectionManager;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.controller.UserSessionUtils;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    private DispatcherServlet dispatcher;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());

        ConnectionHolder.setDataSource(ac.getBean(DataSource.class));
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void annotation_user_list() throws Exception {
        request.setRequestURI("/users");
        request.setMethod("GET");

        dispatcher.service(request, response);

        assertThat(response.getRedirectedUrl()).isNotNull();
    }

    @Test
    void annotation_user_create() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        assertThat(response.getRedirectedUrl()).isEqualTo("/");
    }

    private void createUser(User user) throws Exception {
        request.setRequestURI("/users");
        request.setMethod("POST");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());

        dispatcher.service(request, response);
    }

    @Test
    void login_success() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);

        MockHttpServletRequest secondRequest = new MockHttpServletRequest();
        secondRequest.setRequestURI("/users/login");
        secondRequest.setMethod("POST");
        secondRequest.setParameter("userId", user.getUserId());
        secondRequest.setParameter("password", user.getPassword());
        MockHttpServletResponse secondResponse = new MockHttpServletResponse();

        dispatcher.service(secondRequest, secondResponse);

        assertThat(secondResponse.getRedirectedUrl()).isEqualTo("/");
        assertThat(UserSessionUtils.getUserFromSession(secondRequest.getSession())).isNotNull();
    }
}
