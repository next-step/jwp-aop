package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;
import java.util.Objects;

@Service
public class QnaService {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

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
    public void addAnswer(Answer answer) {
        Answer insertAnswer = answerDao.insert(answer);
        long questionId = insertAnswer.getQuestionId();
        Question question = questionDao.findById(questionId);

        if (Objects.isNull(question)) {
            throw new IllegalArgumentException("존재하지 않는 질문입니다.");
        }
        questionDao.updateCountOfAnswer(questionId);
    }
}
