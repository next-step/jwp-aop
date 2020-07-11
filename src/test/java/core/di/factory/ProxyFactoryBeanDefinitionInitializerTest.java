package core.di.factory;

import core.di.beans.factory.BeanInitializer;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.ProxyFactoryBeanDefinition;
import core.di.beans.factory.ProxyFactoryBeanDefinitionInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanDefinitionInitializerTest {

    private BeanInitializer beanInitializer;

    @BeforeEach
    public void setUp() {
        beanInitializer = new ProxyFactoryBeanDefinitionInitializer();
    }

    @Test
    public void initBeanTest() {
        ProxyFactoryBeanDefinition beanDefinition = new ProxyFactoryBeanDefinition(
                MyService.class.getName(),
                MyService.class,
                method -> true,
                (obj, method, args, proxy) -> proxy.invokeSuper(obj, args)
        );

        MyService myService = (MyService) beanInitializer.instantiate(beanDefinition, new DefaultBeanFactory());

        assertThat(myService).isNotNull();
    }


    public static class MyService {

    }
}
