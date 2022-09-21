package study.proxy.transaction;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import next.model.Answer;
import next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

    private ApplicationContext ac;
    private TransactionalQnaService transactionalQnaService;

    @BeforeEach
    void setUp() {
        ac = new AnnotationConfigApplicationContext(MyConfiguration.class, TransactionalQnaService.class);
        transactionalQnaService = ac.getBean(TransactionalQnaService.class);
    }

    @Test
    void transactionTest() {
        Answer answer = new Answer("commenterCatsbi", "commentTest", 1);

        transactionalQnaService.addAnswer(1, answer, false);
        Question question = transactionalQnaService.findQuestionById(1L);

        assertThat(question.getCountOfComment()).isEqualTo(1);
        transactionalQnaService.addAnswer(1, answer, true);

        question = transactionalQnaService.findQuestionById(1L);
        assertThat(question.getCountOfComment()).isEqualTo(1);
    }
}
