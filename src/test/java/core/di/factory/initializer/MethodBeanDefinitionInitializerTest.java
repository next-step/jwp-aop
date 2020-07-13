package core.di.factory.initializer;

import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.definition.ClassBeanDefinition;
import core.di.beans.factory.definition.MethodBeanDefinition;
import core.di.beans.factory.initializer.BeanInitializer;
import core.di.beans.factory.initializer.MethodBeanDefinitionInitializer;
import core.di.factory.example2.MyBean;
import core.di.factory.example2.TestConfiguration2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class MethodBeanDefinitionInitializerTest {

    private DefaultBeanFactory beanFactory;
    private BeanInitializer beanInitializer;

    @BeforeEach
    public void setUp() throws NoSuchMethodException {
        beanInitializer = new MethodBeanDefinitionInitializer();
        beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(new ClassBeanDefinition(TestConfiguration2.class, TestConfiguration2.class.getName()));
    }

    @Test
    @DisplayName("@Bean 메서드 인스턴스 생성 테스트")
    public void initBeanTest() throws NoSuchMethodException {
        MethodBeanDefinition beanDefinition = new MethodBeanDefinition(TestConfiguration2.class.getMethod("myBean"));

        MyBean myBean = (MyBean) beanInitializer.instantiate(beanDefinition, beanFactory);

        assertThat(beanInitializer.support(beanDefinition)).isTrue();
        assertThat(myBean).isNotNull();
    }
}
