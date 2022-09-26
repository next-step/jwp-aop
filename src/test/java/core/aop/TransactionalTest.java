package core.aop;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.TestDatabaseConfig;
import next.model.Answer;
import next.service.QnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransactionalTest {
    private QnaService qnaService;

    @BeforeEach
    void setup() {
        qnaService = ((ApplicationContext) new AnnotationConfigApplicationContext(TestDatabaseConfig.class)).getBean(QnaService.class);
    }

    @Test
    @DisplayName("비즈니스 로직이 정상적으로 처리되면 commit이 수행된다.")
    void commit() {
        long questionId = 1L;
        Answer answer = new Answer("Writer", "답변", questionId);

        qnaService.addAnswer(answer);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).hasSize(1);
    }

    @Test
    @DisplayName("Exception 발생시 Transaction rollback")
    void rollback() {

        long questionId = 100L;
        Answer answer = new Answer("Writer", "답변", questionId);

        assertThatThrownBy(() -> qnaService.addAnswer(answer))
                .isInstanceOf(RuntimeException.class);

        List<Answer> answers = qnaService.findAllByQuestionId(questionId);
        assertThat(answers).isEmpty();
    }
}
