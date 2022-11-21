package core.aop.transaction;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.ExampleConfig;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.model.Answer;

class BeforeAdviceTest {

    private ApplicationContext ac;

    private AnswerDao answerDao;

    @BeforeEach
    void setUp() throws SQLException {
        ac = new AnnotationConfigApplicationContext(TransactionTestConfig.class);

        answerDao = ac.getBean(AnswerDao.class);
        answerDao.deleteAll();
    }

    @DisplayName("(통합-트랜잭션) 답변 생성 후 예외 발생시, 답변이 생성되지 않는다.")
    @Test
    void name() throws CannotDeleteException {
        var questionId = 1L;
        var qnaService = ac.getBean(TransactionQnaService.class);

        try {
            qnaService.addAnswerWithException(questionId, new Answer("test", "content", questionId));
        } catch (Exception e) {
        }

        var actual = answerDao.count();
        assertThat(actual).isEqualTo(0);
    }

    @DisplayName("(통합-트랜잭션) 답변을 생성할 수 있다.")
    @Test
    void name3() throws CannotDeleteException {
        var questionId = 1L;
        var qnaService = ac.getBean(TransactionQnaService.class);

        qnaService.addAnswer(questionId, new Answer("test", "content", questionId));

        var actual = answerDao.count();
        assertThat(actual).isEqualTo(1);
    }

    @DisplayName("(통합-트랜잭션X) 답변 생성 후 예외 발생시, 답변이 남아있는다.")
    @Test
    void name2() throws CannotDeleteException {
        var questionId = 1L;
        var qnaService = ac.getBean(TransactionQnaService.class);

        try {
            qnaService.addAnswerWithException2(questionId, new Answer("test", "content", questionId));
        } catch (Exception e) {
        }

        var actual = answerDao.count();
        assertThat(actual).isEqualTo(1);
    }
}