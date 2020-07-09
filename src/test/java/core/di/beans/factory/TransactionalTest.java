package core.di.beans.factory;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionalTest {

    private Target target;
    private QuestionDao questionDao;
    private AnswerDao answerDao;
    private long questionId;

    @BeforeEach
    void setUp() {
        final ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class, Target.class);
        target = context.getBean(Target.class);
        questionDao = context.getBean(QuestionDao.class);
        answerDao = context.getBean(AnswerDao.class);
        final Question insertedQuestion = questionDao.insert(new Question("chwon", "title", "content"));
        questionId = insertedQuestion.getQuestionId();
    }

    @DisplayName("트랜잭션이 제대로 커밋되는지 테스트한다.")
    @Test
    void transational_commit() {

        // when
        target.addAnswer(new Answer("hi", "hi", questionId), false);

        // then
        final List<Answer> allByQuestionId = answerDao.findAllByQuestionId(questionId);
        final Question question = questionDao.findById(questionId);
        assertThat(allByQuestionId.size()).isEqualTo(1);
        assertThat(question.getCountOfComment()).isEqualTo(1);
    }

    @DisplayName("트랜잭션이 제대로 롤백되는지 테스트한다.")
    @Test
    void transational_rollback() {

        // when
        target.addAnswer(new Answer("hi", "hi", questionId), true);

        // then
        final List<Answer> allByQuestionId = answerDao.findAllByQuestionId(questionId);
        final Question question = questionDao.findById(questionId);
        assertThat(allByQuestionId.size()).isEqualTo(0);
        assertThat(question.getCountOfComment()).isEqualTo(0);
    }

    @Service
    public static class Target {

        private QuestionDao questionDao;
        private AnswerDao answerDao;

        public Target() {
        }

        @Inject
        public Target(QuestionDao questionDao, AnswerDao answerDao) {
            this.questionDao = questionDao;
            this.answerDao = answerDao;
        }

        @Transactional
        public void addAnswer(Answer answer, boolean throwsException) {
            answerDao.insert(answer);
            if (throwsException) {
                throw new RuntimeException("던질까말까던질까말까");
            }
            questionDao.updateCountOfAnswer(answer.getQuestionId());
        }
    }
}
