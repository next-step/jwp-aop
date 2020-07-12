package core.di.factory.scanner;

import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.definition.BeanDefinitionRegistry;
import core.di.beans.factory.scanner.MethodBeanScanner;
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
