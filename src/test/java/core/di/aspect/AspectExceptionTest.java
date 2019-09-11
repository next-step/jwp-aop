package core.di.aspect;

import core.di.aspect.example.AspectUserService;
import core.di.aspect.example.AuthenticdationService;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AspectExceptionTest {

    private AnnotationConfigApplicationContext ac;

    @BeforeEach
    void setUp() {
        this.ac = new AnnotationConfigApplicationContext(AspectConfig.class);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, this.ac.getBean(DataSource.class));
    }

    @DisplayName("Aspect with Exception")
    @Test
    void aspectWithException() {
        User user = new User("jun", "1234", "hyunjun", "jun@test.com");
        AuthenticdationService.IS_LOGIN = false;

        final AspectUserService userService = ac.getBean(AspectUserService.class);
        assertThrows(RuntimeException.class, () -> userService.addAndGetUser(user));
    }

}
