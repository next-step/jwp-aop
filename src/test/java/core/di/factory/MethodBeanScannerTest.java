package core.di.factory;

import core.di.beans.factory.BeanDefinitionRegistry;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.MethodBeanScanner;
import core.di.factory.example.MyJdbcTemplate;
import core.di.factory.example2.MyBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class MethodBeanScannerTest {

    BeanDefinitionRegistry beanDefinitionRegistry;

    @BeforeEach
    public void setUp() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();

        MethodBeanScanner methodBeanScanner = new MethodBeanScanner(beanFactory);
        methodBeanScanner.scan("core.di.factory.example2");

        beanDefinitionRegistry = beanFactory;
    }

    @Test
    public void registerMethodBeanDefinitionTest() {
        assertThat(beanDefinitionRegistry.getBeanDefinitions(MyBean.class)).isNotEmpty();
        assertThat(beanDefinitionRegistry.getBeanDefinition("myBean")).isNotNull();
    }
}
