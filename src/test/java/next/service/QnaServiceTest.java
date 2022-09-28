package next.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.DataAccessException;
import java.util.List;
import next.config.MyConfiguration;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QnaServiceTest {

    private QnaService qnaService;
    private AnswerDao answerDao;
    private QuestionDao questionDao;

    @BeforeEach
    void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        qnaService = applicationContext.getBean(QnaService.class);
        answerDao = applicationContext.getBean(AnswerDao.class);
        questionDao = applicationContext.getBean(QuestionDao.class);
    }

    @DisplayName("답변과 답변의 수가 모두 정상적으로 커밋된다.")
    @Test
    void commit() {
        long questionId = 1L;
        final List<Answer> originAnswers = answerDao.findAllByQuestionId(questionId);

        assertThat(originAnswers).isEmpty();

        Answer answer = new Answer("YONGJU", "이번 과정은", questionId);

        qnaService.addAnswer(questionId, answer);

        final List<Answer> actual = answerDao.findAllByQuestionId(questionId);
        assertThat(actual).hasSize(1);

        final Question question = questionDao.findById(questionId);
        assertThat(question.getCountOfComment()).isEqualTo(1);
    }

    @DisplayName("답변의 수를 등록하지 못하여 예외가 발생하면 롤백된다. 등록하려던 답변도 등록되지 않는다.")
    @Test
    void rollback() {
        long questionId = 10000L;
        final List<Answer> originAnswers = answerDao.findAllByQuestionId(questionId);

        assertThat(originAnswers).isEmpty();

        Answer answer = new Answer("YONGJU", "정말 쉽지 않다", questionId);

        assertThatThrownBy(() -> qnaService.addAnswer(questionId, answer)).isInstanceOf(DataAccessException.class);

        final List<Answer> actual = answerDao.findAllByQuestionId(questionId);
        assertThat(actual).isEmpty();
    }

}
