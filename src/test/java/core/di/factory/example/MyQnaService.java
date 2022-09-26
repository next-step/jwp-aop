package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;
import core.annotation.Transactional;

@Service
public class MyQnaService {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    @Inject
    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    @Transactional
    public void getQuestion() {

    }
}
