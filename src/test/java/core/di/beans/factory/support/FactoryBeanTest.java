package core.di.beans.factory.support;

import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import core.di.factory.example.FactoryNotTestService;
import core.di.factory.example.FactoryTestService;
import core.di.factory.example.ProxyInValidFactoryBeanService;
import core.di.factory.example.ProxyValidFactoryBeanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

        assertSame(factoryBean1, factoryBean2);
    }

    @Test
    void proxyFactoryBean() {
        final FactoryTestService factoryBean = beanFactory.getBean(FactoryTestService.class);
        String valid = factoryBean.log();
        assertThat(valid).isEqualTo("ok");
        assertTrue(ProxyValidFactoryBeanService.isBeforeExecuted);
        assertTrue(ProxyValidFactoryBeanService.isAfterExecuted);

        final FactoryNotTestService factoryNotProxyBean =
                beanFactory.getBean(FactoryNotTestService.class);
        String invalid = factoryNotProxyBean.log();
        assertThat(invalid).isEqualTo("not ok");
        assertFalse(ProxyInValidFactoryBeanService.isBeforeExecuted);
        assertFalse(ProxyInValidFactoryBeanService.isAfterExecuted);
    }

}
