package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.PostConstruct;
import core.annotation.Service;
import core.annotation.Transactional;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.support.BeanFactoryAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MyQnaService implements BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(MyQnaService.class);

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private BeanFactory beanFactory;

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
    public void addAnswer() {
        logger.info("MyQnaService.addAnswer()");
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("MyQnaService.postConstruct()");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
