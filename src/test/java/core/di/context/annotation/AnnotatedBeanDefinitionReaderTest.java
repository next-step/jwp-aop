package core.di.context.annotation;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.factory.example.ExampleConfig;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyJdbcTemplate;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AnnotatedBeanDefinitionReaderTest {
    @Test
    public void register_simple() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);
        beanFactory.preInstantiateSinglonetons();

        assertThat(beanFactory.getBean(DataSource.class)).isNotNull();
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
        cbds.doScan("core.di.factory.example");

        beanFactory.preInstantiateSinglonetons();

        assertNotNull(beanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
        assertThat(userRepository).isNotNull();
        assertThat(userRepository.getDataSource()).isNotNull();

        MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
        assertThat(jdbcTemplate).isNotNull();
        assertThat(jdbcTemplate.getDataSource()).isNotNull();
    }
}
