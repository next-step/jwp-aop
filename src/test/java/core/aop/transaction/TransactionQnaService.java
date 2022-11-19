package core.aop.transaction;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import core.jdbc.JdbcTemplate;
import next.dao.AnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.model.Answer;

@Service
public class TransactionQnaService {
    private AnswerDao answerDao;
    private JdbcTemplate jdbcTemplate;
    private QuestionDao questionDao;

    protected TransactionQnaService() {
    }

    @Inject
    public TransactionQnaService(AnswerDao answerDao, JdbcTemplate jdbcTemplate, QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.jdbcTemplate = jdbcTemplate;
        this.questionDao = questionDao;
    }

    @Transactional
    public void addAnswerWithException(long questionId, Answer answer) {
        var questionDao = new ExceptionalQuestionDao(jdbcTemplate);

        answerDao.insert(answer);
        questionDao.updateCountOfAnswer(questionId);
    }

    public void addAnswerWithException2(long questionId, Answer answer) {
        var questionDao = new ExceptionalQuestionDao(jdbcTemplate);

        answerDao.insert(answer);
        questionDao.updateCountOfAnswer(questionId);
    }

    @Transactional
    public void addAnswer(long questionId, Answer answer) {
        answerDao.insert(answer);
        questionDao.updateCountOfAnswer(questionId);
    }

    private static class ExceptionalQuestionDao extends JdbcQuestionDao {

        @Inject
        public ExceptionalQuestionDao(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate);
        }

        @Override
        public void updateCountOfAnswer(long questionId) {
            throw new UnsupportedOperationException();
        }
    }

}
