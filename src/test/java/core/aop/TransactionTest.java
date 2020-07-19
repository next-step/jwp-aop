package core.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import core.aop.transaction.TransactionBeanPostProcessor;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import java.util.Arrays;
import next.config.MyConfiguration;
import next.dao.UserDao;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

   private ApplicationContext ac;

    @BeforeEach
    public void setUp() {
        ac = new AnnotationConfigApplicationContext(Arrays.asList(new TransactionBeanPostProcessor()), MyConfiguration.class);
    }

    @Test
    void transaction(){
        TransactionService transactionService = ac.getBean(TransactionService.class);

        User user = new User("javajigi", "password", "홍길동", "a@a.com");

        assertThatThrownBy(()->transactionService.addUserThenThrowException(user)).isInstanceOf(Exception.class);
        assertThat(ac.getBean(UserDao.class).findByUserId("javagivi")).isNull();
    }
}
