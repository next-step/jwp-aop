package next.service;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import java.util.Date;
import next.config.MyConfiguration;
import next.model.Answer;
import next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QnaServiceTest {

    private QnaService qnaService;

    @BeforeEach
    void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);

        this.qnaService = ac.getBean(QnaService.class);
    }

    @Test
    void injectTest() {
        assertThat(qnaService).isNotNull();
    }

    @Test
    void findTest() {
        Question foundQuestion = qnaService.findById(1);

        assertThat(foundQuestion.getWriter()).isEqualTo("자바지기");
    }

    @DisplayName("Question 에 Answer 을 추가하면 코멘트 개수가 증가한다.")
    @Test
    void addAnswerTest1() {
        final Long questionId = 7L;
        final int initialCount = 2;
        Answer answer = new Answer("testWriter", "testContents", questionId);

        qnaService.addAnswer(answer);
        Question foundQuestion = qnaService.findById(questionId);

        assertThat(foundQuestion.getCountOfComment()).isEqualTo(initialCount + 1);
    }

    @DisplayName("Question 에 Answer 추가하고 코멘트 개수 증가 중 에러가 나면 추가했던 것이 롤백된다.")
    @Test
    void addAnswerTest2() {
        final Long questionId = 7L;
        final int initialCount = 2;
        Answer answer = new Answer("testWriter", "testContents", questionId);

        qnaService.addAnswer(answer);
        Question foundQuestion = qnaService.findById(questionId);

        assertThat(foundQuestion.getCountOfComment()).isEqualTo(initialCount);
    }

}
