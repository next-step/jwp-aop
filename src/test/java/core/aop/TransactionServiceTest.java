package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import next.dto.UserUpdatedDto;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class TransactionServiceTest {

    BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

    @Test
    @DisplayName("트랜잭션 롤백 테스트")
    public void transactionTest() {
        final String userId = "as";
        TransactionService transactionService = beanFactory.getBean(TransactionService.class);

        User user = new User(userId, "as", "KingCjy", "as@as.as");
        transactionService.addUser(user);

//        ERROR ROLLBACK
        transactionService.update(new UserUpdatedDto(userId, "as", "admin", "admin@admin.com"));

        User result = transactionService.findUserById(userId);

        assertThat(result.getName()).isEqualTo("KingCjy");
        assertThat(result.getEmail()).isEqualTo("as@as.as");
    }
}
