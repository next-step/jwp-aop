package core.di.beans.factory.support;

import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.FactoryTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryBeanTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.di.factory.example");
        beanFactory.preInstantiateSinglonetons();
    }

    @Test
    void singletonFactoryBean() {
        final FactoryTestService factoryBean1 = beanFactory.getBean(FactoryTestService.class);
        final FactoryTestService factoryBean2 = beanFactory.getBean(FactoryTestService.class);

        assertTrue(factoryBean1 == factoryBean2);
    }

}
