package core.di.context.annotation;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.factory.example.ExampleConfig;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyJdbcTemplate;
import study.proxy.ProxyConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AnnotatedBeanDefinitionReaderTest {
    @Test
    public void register_simple() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);
        beanFactory.preInstantiateSingletons();

        assertThat(beanFactory.getBean(DataSource.class)).isNotNull();
    }

    @Test
    public void register_ClasspathBeanDefinitionScanner_통합() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(IntegrationConfig.class);

        ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(beanFactory);
        cbds.doScan("core.di.factory.example");

        beanFactory.preInstantiateSingletons();

        assertNotNull(beanFactory.getBean(DataSource.class));

        JdbcUserRepository userRepository = beanFactory.getBean(JdbcUserRepository.class);
        assertThat(userRepository).isNotNull();
        assertThat(userRepository.getDataSource()).isNotNull();

        MyJdbcTemplate jdbcTemplate = beanFactory.getBean(MyJdbcTemplate.class);
        assertThat(jdbcTemplate).isNotNull();
        assertThat(jdbcTemplate.getDataSource()).isNotNull();
    }

    @DisplayName("프록시 빈 등록")
    @Test
    void proxy_bean() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ProxyConfig.class);
        beanFactory.preInstantiateSingletons();

        final HelloService cglibProxyBean = beanFactory.getBean(HelloService.class);
        final Object jdkProxyBean = beanFactory.getBean(HelloTarget.class);

        assertThat(cglibProxyBean.getClass().getName()).contains("$$EnhancerByCGLIB$$");
        assertThat(jdkProxyBean.getClass().getName()).contains("$Proxy");
    }
}
