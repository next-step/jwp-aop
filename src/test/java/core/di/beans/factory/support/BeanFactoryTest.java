package core.di.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.FactoryBeanConfig;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.cglib.HelloTarget;

class BeanFactoryTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(FactoryBeanConfig.class);

        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");
        beanFactory.preInstantiateSingletons();
    }

    @DisplayName("FactoryBean 구현체 Proxy연결 테스트")
    @Test
    void factoryBeanProxyTest() {
        HelloTarget target = beanFactory.getBean(HelloTarget.class);

        assertThat(target.sayHi("schulz")).isEqualTo("HI SCHULZ");
        assertThat(target.sayHello("schulz")).isEqualTo("HELLO SCHULZ");
        assertThat(target.sayThankYou("schulz")).isEqualTo("THANK YOU SCHULZ");
        assertThat(target.pingpong("schulz")).isEqualTo("Pong schulz");
    }

    @Test
    void constructorDI() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);
        assertThat(qnaController).isNotNull();
        assertThat(qnaController.getQnaService()).isNotNull();

        MyQnaService qnaService = qnaController.getQnaService();
        assertThat(qnaService.getUserRepository()).isNotNull();
        assertThat(qnaService.getQuestionRepository()).isNotNull();
    }

    @Test
    void fieldDI() throws Exception {
        MyUserService userService = beanFactory.getBean(MyUserService.class);
        assertThat(userService).isNotNull();
        assertThat(userService.getUserRepository()).isNotNull();
    }

    @Test
    void setterDI() throws Exception {
        MyUserController userController = beanFactory.getBean(MyUserController.class);

        assertThat(userController);
        assertThat(userController.getUserService()).isNotNull();
        ;
    }

    @AfterEach
    void tearDown() {
        beanFactory.clear();
    }
}
