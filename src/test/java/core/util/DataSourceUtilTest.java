package core.util;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class DataSourceUtilTest {
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);
        beanFactory.preInstantiateSingletons();

        dataSource = beanFactory.getBean(DataSource.class);
    }

    @Test
    void getConnection() {
        Connection connection = DataSourceUtil.getConnection(dataSource);

        assertThat(connection).isNotNull();
    }
}