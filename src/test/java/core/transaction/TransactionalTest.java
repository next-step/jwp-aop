package core.transaction;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.DataAccessException;
import next.config.MyConfiguration;
import next.model.Answer;
import next.service.QnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionalTest {
    private QnaService qnaService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        qnaService = applicationContext.getBean(QnaService.class);
    }

    @DisplayName("QnaService 의 addAnswer 메서드가 정상적으로 실행되면 트랜잭션이 commit 된다.")
    @Test
    void commit() throws Exception {
        long questionId = 1L;
        Answer answer = new Answer("wu2ee", "만들면서 배우는 Spring", questionId);

        qnaService.addAnswer(questionId, answer);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).hasSize(1);
    }

    @DisplayName("QnaService 의 addAnswer 메서드에서 예외가 발생하면 트랜잭션이 rollback 된다.")
    @Test
    void rollback() {
        long questionId = 9L;
        Answer answer = new Answer("wu2ee", "만들면서 배우는 Spring", questionId);
        assertThatThrownBy(() -> qnaService.addAnswer(questionId, answer)).isInstanceOf(DataAccessException.class);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).isEmpty();
    }

    @DisplayName("@Transactional 애노테이션을 사용하지 않은 addAnswerNoTransactional 메서드에서 예외가 발생하면 트랜잭션이 rollback 되지 않는다.")
    @Test
    void noRollback() {
        long questionId = 9L;
        Answer answer = new Answer("wu2ee", "만들면서 배우는 Spring", questionId);
        assertThatThrownBy(() -> qnaService.addAnswerNoTransactional(questionId, answer)).isInstanceOf(DataAccessException.class);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).hasSize(1);
    }
}
