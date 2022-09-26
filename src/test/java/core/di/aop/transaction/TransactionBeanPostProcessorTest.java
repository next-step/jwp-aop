package core.di.aop.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.JdbcQuestionRepository;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QuestionRepository;
import core.di.factory.example.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloService;

class TransactionBeanPostProcessorTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");
        beanFactory.preInstantiateSingletons();
    }

    @DisplayName("@Transaction 애너테이션이 적용된 빈을 프록시 객체로 변환한다")
    @Test
    void postInitialization() {
        final TransactionBeanPostProcessor transactionBeanPostProcessor = new TransactionBeanPostProcessor(beanFactory);

        final UserRepository userRepository = new JdbcUserRepository();
        final QuestionRepository questionRepository = new JdbcQuestionRepository();
        final MyQnaService myQnaService = new MyQnaService(userRepository, questionRepository);

        final MyQnaService actual = (MyQnaService) transactionBeanPostProcessor.postInitialization(myQnaService);

        assertThat(actual.getClass().getName()).contains("CGLIB$$");
    }

    @DisplayName("@Transaction 애너테이션이 없다면 빈을 그대로 반환한다")
    @Test
    void postInitializationNotProxy() {
        final TransactionBeanPostProcessor transactionBeanPostProcessor = new TransactionBeanPostProcessor(beanFactory);
        final HelloService actual = (HelloService) transactionBeanPostProcessor.postInitialization(new HelloService());

        assertThat(actual.getClass().getName()).doesNotContain("CGLIB$$");
    }
}
