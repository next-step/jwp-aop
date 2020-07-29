package core.di.context.support;

import core.di.context.ApplicationContext;
import next.model.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationConfigApplicationContextTest {

    @DisplayName("Transactional 메소드 실행 중 Exception 이 발생하면 rollback 된다.")
    @Test
    void txProxyFactoryBean() {
        /* given */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxProxyTestConfig.class);
        TxProxyTestService service = applicationContext.getBean(TxProxyTestService.class);

        List<Question> allQuestions = service.findAll();
        int beforeSize = allQuestions.size();

        Question question = new Question("nick", "testTitle", "testContents");

        /* when */
        assertThrows(RuntimeException.class, () -> service.createThrowsException(question));

        /* then */
        assertThat(service.findAll()).hasSize(beforeSize);
    }

}
