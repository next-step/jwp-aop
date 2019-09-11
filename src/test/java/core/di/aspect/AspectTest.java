package core.di.aspect;

import core.di.aspect.example.*;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AspectTest {

    private AnnotationConfigApplicationContext ac;

    @BeforeEach
    void setUp() {
        this.ac = new AnnotationConfigApplicationContext(AspectConfig.class);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, this.ac.getBean(DataSource.class));
    }

    @DisplayName("Aspect with Order Test")
    @Test
    void aspect() {
        User user = new User("jun", "1234", "hyunjun", "jun@test.com");
        AuthenticdationService.IS_LOGIN = true;

        final AspectUserService userService = ac.getBean(AspectUserService.class);
        userService.addAndGetUser(user);

        assertEquals(AuthenticationAspect.BEFORE_COUNT, 1);
        assertEquals(LogAspect.BEFORE_COUNT, 2);
        assertEquals(TimeAspect.BEFORE_COUNT, 3);
        assertEquals(TimeAspect.AFTER_COUNT, 4);
        assertEquals(LogAspect.AFTER_COUNT, 5);
        assertEquals(AuthenticationAspect.AFTER_COUNT, 6);
    }


}
