package core.di.beans.factory.support;

import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.ExampleConfig;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import study.dynamicProxy.Hello;
import study.dynamicProxy.HelloTarget;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryTest {
    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");

        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);

        beanFactory.preInstantiateSingletons();
    }

    @Test
    public void constructorDI() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);
        assertThat(qnaController).isNotNull();
        assertThat(qnaController.getQnaService()).isNotNull();

        MyQnaService qnaService = qnaController.getQnaService();
        assertThat(qnaService.getUserRepository()).isNotNull();
        assertThat(qnaService.getQuestionRepository()).isNotNull();
    }

    @Test
    public void fieldDI() throws Exception {
        MyUserService userService = beanFactory.getBean(MyUserService.class);
        assertThat(userService).isNotNull();
        assertThat(userService.getUserRepository()).isNotNull();
    }

    @Test
    public void setterDI() throws Exception {
        MyUserController userController = beanFactory.getBean(MyUserController.class);

        assertThat(userController);
        assertThat(userController.getUserService()).isNotNull();;
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }

    @Test
    @DisplayName("FactoryBean 구현체를 통한 Proxy 연결 테스트")
    public void beanFactoryTest() {
        Hello instance = beanFactory.getBean(HelloTarget.class);

        assertThat(instance).isNotNull();
        assertThat(instance.sayHello("dhlee")).isEqualTo("HELLO DHLEE");
        assertThat(instance.sayHi("dhlee")).isEqualTo("HI DHLEE");
        assertThat(instance.sayThankYou("dhlee")).isEqualTo("THANK YOU DHLEE");
        assertThat(instance.pingpong("dhlee")).isEqualTo("Pong dhlee");
    }
}
