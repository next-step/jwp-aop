package core.di.context.support;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import next.model.Question;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TxProxyTestService {

    private TxProxyTestQuestionDao questionDao;

    @Inject
    public TxProxyTestService(TxProxyTestQuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Transactional
    public void create(Question question) {
        questionDao.insert(question);
    }

    @Transactional
    public void createThrowsException(Question question) {
        questionDao.insert(question);

        throw new RuntimeException();
    }

    public List<Question> findAll() {
        return questionDao.findAll();
    }

}
