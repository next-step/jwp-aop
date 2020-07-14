package core.di.beans.factory.support;

import core.di.beans.factory.aop.ProxyBeanDefinition;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.SayMethodMatcher;
import study.cglib.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryTest {
    private String hello_expected = "HELLO EESEUL";
    private String hi_expected = "HI EESEUL";
    private String thankYou_expected = "THANK YOU EESEUL";
    private String pingPong_expected = "Pong eeseul";

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");

        MethodMatcher matcher = new SayMethodMatcher();
        ProxyBeanDefinition proxyBeanDefinition = new ProxyBeanDefinition(HelloTarget.class, new MyAdvice(matcher));
        beanFactory.registerBeanDefinition(HelloTarget.class, proxyBeanDefinition);

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
        assertThat(userController.getUserService()).isNotNull();
    }

    @Test
    void proxyBean() {
        HelloTarget helloTarget = beanFactory.getBean(HelloTarget.class);

        String hello_actual = helloTarget.sayHello("eeseul");
        String hi_actual = helloTarget.sayHi("eeseul");
        String thankYou_actual = helloTarget.sayThankYou("eeseul");
        String pingPong_actual = helloTarget.pingPong("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
        assertThat(pingPong_actual).isEqualTo(pingPong_expected);
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }
}
