package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import core.jdbc.DataAccessException;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;

@Service
public class QnaService {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public QnaService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public Question findById(long questionId) {
        return questionDao.findById(questionId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return answerDao.findAllByQuestionId(questionId);
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (question.canDelete(user, answers)) {
            questionDao.delete(questionId);
        }
    }

    @Transactional
    public Answer addAnswer(long questionId, Answer answer) {
        Answer savedAnswer = answerDao.insert(answer);
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new DataAccessException("질문이 없으므로 답변을 달 수 없습니다.");
        }
        questionDao.updateCountOfAnswer(questionId);
        return savedAnswer;
    }

    public Answer addAnswerNoTransactional(long questionId, Answer answer) {
        Answer savedAnswer = answerDao.insert(answer);
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new DataAccessException("질문이 없으므로 답변을 달 수 없습니다.");
        }
        questionDao.updateCountOfAnswer(questionId);
        return savedAnswer;
    }
}
