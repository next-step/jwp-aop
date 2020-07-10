package core.di.beans.factory.support;

import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryTest {
    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");
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

    @Test
    public void factoryBeanTest() {
        ExampleService exampleService = beanFactory.getBean(ExampleService.class);

        assertThat(exampleService).isNotNull();
        assertThat(exampleService.getMessage()).isEqualTo("Hello World");
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }
}
