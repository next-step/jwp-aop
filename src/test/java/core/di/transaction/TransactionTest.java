package core.di.transaction;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.transaction.example.TUserService;
import next.dto.UserUpdatedDto;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private AnnotationConfigApplicationContext ac;
    private User user;
    private UserUpdatedDto updatedUser;

    @BeforeEach
    void setUp() {
        this.ac = new AnnotationConfigApplicationContext(TransactionConfig.class);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, this.ac.getBean(DataSource.class));
        user = new User("jun", "1234", "hyunjun", "jun@test.com");
        updatedUser = new UserUpdatedDto("2345", "lion", "animal@test.com");
    }

    @DisplayName("Transaction Test")
    @Test
    void user_transaction() {
        final TUserService userService = this.ac.getBean(TUserService.class);

        try {
            userService.insertAndModify(user, updatedUser);
        } catch (RuntimeException e) {
            assertNull(userService.getUser("jun"));
            return;
        }

        fail();
    }

    @DisplayName("Not Transaction Test")
    @Test
    void user_not_transaction() {
        final TUserService userService = this.ac.getBean(TUserService.class);

        try {
            userService.insertAndModifyNotTransaction(user, updatedUser);
        } catch (RuntimeException e) {
            assertNotNull(userService.getUser("jun"));
            return;
        }

        fail();
    }

}
