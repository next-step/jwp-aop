package core.jdbc;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.model.Answer;
import next.service.QnaService;

class TransactionalTest {

    private QnaService qnaService;

    @BeforeEach
    void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TransactionTestConfig.class);
        qnaService = applicationContext.getBean(QnaService.class);
        System.out.println("qnaService = " + qnaService.getClass());
    }

    @DisplayName("비즈니스 로직이 정상적으로 처리되면 commit이 수행된다.")
    @Test
    void commit() {
        long questionId = 1L;
        Answer answer = new Answer("Jack", "만들면서 배우는 Spring", questionId);

        qnaService.addAnswer(questionId, answer);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).hasSize(1);
    }

    @DisplayName("비즈니스 로직이 정상적으로 처리되지 않으면 rollback이 수행된다.")
    @Test
    void rollback() {
        long questionId = 999L; // 존재하지 않는 질문 id
        Answer answer = new Answer("Jack", "만들면서 배우는 Spring", questionId);

        assertThatThrownBy(() -> qnaService.addAnswer(questionId, answer))
            .isInstanceOf(RuntimeException.class);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).isEmpty();
    }
}
