package core.di.factory;

import core.di.beans.factory.*;
import core.di.factory.example.TestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

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
        beanFactory.registerDefinition(new ClassBeanDefinition(TestConfiguration.class, TestConfiguration.class.getName()));
    }
}
