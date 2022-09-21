package study.proxy.transaction;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

@Service
class TransactionalQnaService {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Inject
    public TransactionalQnaService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Transactional
    public void addAnswer(long questionId, Answer answer, boolean exceptionFlag) {
        Answer createdAnswer = answerDao.insert(answer);

        if (exceptionFlag) {
            throw new RuntimeException();
        }
        questionDao.updateCountOfAnswer(questionId);
    }

    @Transactional
    public Long addQuestion(Question question) {
        Question createdQuestion = questionDao.insert(question);

        return createdQuestion.getQuestionId();
    }

    @Transactional
    public Question findQuestionById(long id) {
        return questionDao.findById(id);
    }

    @Transactional
    public Answer findAnswerById(long id) {
        return answerDao.findById(id);
    }
}