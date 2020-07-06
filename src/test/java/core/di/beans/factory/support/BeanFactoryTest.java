package core.di.beans.factory.support;

import core.annotation.Component;
import core.annotation.Inject;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.HelloTarget;

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

    @DisplayName("ProxyFactoryBean을 등록해서 Bean을 잘 가지고 오는지 확인해보자.")
    @Test
    public void getProxyFactoryBean() throws Exception {

        // given
        final ProxyBeanDefinition pbd = new ProxyBeanDefinition(IAmATarget.class);
        beanFactory.registerBeanDefinition(IAmATarget.class, pbd);

        // when
        final IAmATarget bean = beanFactory.getBean(IAmATarget.class);

        // then
        final String result = bean.say();
        assertThat(bean).isNotNull();
        assertThat(result).isEqualTo("Hello World");
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }

    public static class IAmATarget {
        private IAmADep iAmADep;

        public IAmATarget() {
        }

        @Inject
        public IAmATarget(IAmADep iAmADep) {
            this.iAmADep = iAmADep;
        }

        public String say() {
            return iAmADep.sayHello();
        }
    }

    @Component
    public static class IAmADep {
        public String sayHello() {
            return "Hello World";
        }
    }
}
