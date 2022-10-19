package core.di.beans.factory.support;

import core.annotation.Transactional;
import core.aop.Advisor;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionalBeanPostProcessorTest {
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        dataSource = applicationContext.getBean(DataSource.class);
    }

    @DisplayName("@Transactional 애노테이션이 있는 클래스는 빈 후처리를 통해 프록시를 반환한다.")
    @Test
    void proxyPostProcess() {
        BeanPostProcessor beanPostProcessor = new TransactionalBeanPostProcessor(dataSource);
        Object preProcessingBean = new TransactionalClass();
        Object postProcessedBean = beanPostProcessor.postProcessAfterInitialization(preProcessingBean);
        assertThat(preProcessingBean).isNotEqualTo(postProcessedBean);
    }

    @DisplayName("@Transactional 애노테이션이 없는 클래스는 빈 후처리시 해당 빈을 그대로 반환한다.")
    @Test
    void originPostProcess() {
        BeanPostProcessor beanPostProcessor = new TransactionalBeanPostProcessor(dataSource);
        Object preProcessingBean = new NoTransactionalClass();
        Object postProcessedBean = beanPostProcessor.postProcessAfterInitialization(preProcessingBean);
        assertThat(preProcessingBean).isEqualTo(postProcessedBean);
    }

    @Transactional
    class TransactionalClass {
    }

    class NoTransactionalClass {
        public void noTransactionalMethod() {
        }
    }
}