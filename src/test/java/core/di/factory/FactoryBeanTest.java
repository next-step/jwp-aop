package core.di.factory;

import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.IntegrationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FactoryBean 테스트")
public class FactoryBeanTest {

    private static final Logger logger = LoggerFactory.getLogger(FactoryBeanTest.class);

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");

        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(IntegrationConfig.class);

        beanFactory.preInstantiateSingletons();
    }

    @DisplayName("팩토리빈으로 빈 생성 테스트")
    @Test
    void factoryBeanCreationTest() {
        // given
        HelloTarget factoryBean = beanFactory.getBean(HelloTarget.class);

        // when & then
        assertThat(factoryBean).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }
}
