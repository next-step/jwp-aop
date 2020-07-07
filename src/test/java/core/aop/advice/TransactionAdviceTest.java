package core.aop.advice;

import core.aop.advice.example.Dao;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.DataAccessException;
import next.config.MyConfiguration;
import next.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("트랜잭션이 어드바이스 테스트")
class TransactionAdviceTest {
    private AnnotationConfigApplicationContext ac;

    @BeforeEach
    public void setup() {
        ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

    @Test
    @DisplayName("트랜잭션이 잘 동작하는지")
    void transaction() {
        User admin = new User("admin", "password", "자바지기", "admin@slipp.net");
        Dao dao = ac.getBean(Dao.class);

        assertThat(dao.getAdmin())
                .isEqualTo(admin);

        assertThatExceptionOfType(DataAccessException.class).isThrownBy(dao::multipleQuery); //admin을 수정하는 쿼리 실행
        assertThat(dao.getAdmin()).isEqualTo(admin);
    }

}