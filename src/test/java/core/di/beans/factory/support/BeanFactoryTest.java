package core.di.beans.factory.support;

import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import core.transaction.TransactionBeanPostProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(IntegrationConfig.class);
        beanFactory.addBeanPostProcessor(new TransactionBeanPostProcessor(beanFactory.getBean(DataSource.class)));
        beanFactory.preInstantiateSingletons();
    }

    @Test
    void constructorDI() {
        QnaController qnaController = beanFactory.getBean(QnaController.class);
        assertThat(qnaController).isNotNull();
        assertThat(qnaController.getQnaService()).isNotNull();

        MyQnaService qnaService = qnaController.getQnaService();
        assertThat(qnaService.getUserRepository()).isNotNull();
        assertThat(qnaService.getQuestionRepository()).isNotNull();
    }

    @Test
    void fieldDI() {
        MyUserService userService = beanFactory.getBean(MyUserService.class);
        assertThat(userService).isNotNull();
        assertThat(userService.getUserRepository()).isNotNull();
    }

    @Test
    void setterDI() {
        MyUserController userController = beanFactory.getBean(MyUserController.class);

        assertThat(userController);
        assertThat(userController.getUserService()).isNotNull();
    }

    @Test
    @DisplayName("Transactional 이 있는 클래스는 프록시 객체")
    void transactionProxy() {
        //when
        MyQnaService bean = beanFactory.getBean(MyQnaService.class);
        //then
        assertThat(bean.getClass().getSuperclass())
                .isEqualTo(MyQnaService.class);
    }

    @AfterEach
    void tearDown() {
        beanFactory.clear();
    }
}
