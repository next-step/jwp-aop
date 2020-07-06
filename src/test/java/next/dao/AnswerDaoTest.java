package next.dao;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.ConnectionHolder;
import next.config.MyConfiguration;
import next.model.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {
    private static final Logger log = LoggerFactory.getLogger(AnswerDaoTest.class);

    private AnswerDao answerDao;

    @BeforeEach
    public void setup() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        answerDao = ac.getBean(AnswerDao.class);
    }

    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
        Answer answer = answerDao.insert(expected);
        log.debug("Answer : {}", answer);
        assertThat(answer).isNotNull();
    }
}
