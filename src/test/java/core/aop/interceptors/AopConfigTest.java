package core.aop.interceptors;

import core.di.beans.factory.support.BeanDefinitionTest;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.ApplicationContext;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import core.di.factory.example.MyUserService;
import core.di.factory.example.QnaController;
import core.mvc.DispatcherServlet;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import next.reflection.MyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AopConfigTest {
    private static final Logger log = LoggerFactory.getLogger(AopConfigTest.class);

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
    }

    @Test
    public void getName() throws Exception {
        MyTestService myTestService = applicationContext.getBean(MyTestService.class);

        String name = myTestService.getUppercasedName("ninjasul");
        log.debug("name: {}", name);
    }
}
