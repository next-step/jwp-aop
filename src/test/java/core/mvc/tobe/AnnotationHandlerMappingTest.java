package core.mvc.tobe;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.ConnectionHolder;
import next.config.MyConfiguration;
import next.dao.UserDao;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    private UserDao userDao;

    @BeforeEach
    public void setup() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        ConnectionHolder.setDataSource(ac.getBean(DataSource.class));
        ConnectionHolder.releaseConnection();
        userDao = ac.getBean(UserDao.class);
    }

    @Test
    public void create_find() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        ConnectionHolder.releaseConnection();
        assertThat(userDao.findByUserId(user.getUserId())).isEqualTo(user);
        ConnectionHolder.releaseConnection();

        assertThat(userDao.findByUserId(user.getUserId())).isEqualTo(user);
        ConnectionHolder.releaseConnection();

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/profile");
        request.setParameter("userId", user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);
    }
}
