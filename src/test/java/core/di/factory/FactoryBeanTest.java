package core.di.factory;

import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.*;
import next.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest {
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

    @DisplayName("팩토리 빈 생성 테스트")
    @Test
    void factoryBeanCreationTest() {

        // given
        User factoryBeanSample = beanFactory.getBean(User.class);

        // when & then
        assertThat(factoryBeanSample).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        beanFactory.clear();
    }
}
