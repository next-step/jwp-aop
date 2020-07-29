package core.di.context.support;

import next.model.Question;

import java.util.List;

public interface TxProxyTestQuestionDao {

    void insert(Question question);

    List<Question> findAll();

}
