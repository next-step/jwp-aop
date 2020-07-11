package core.di.factory;

import core.annotation.Inject;
import core.di.beans.factory.*;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example3.CarDao;
import core.di.factory.example3.TestFactoryBean;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.doesNotHave;

/**
 * @author KingCjy
 */
public class FactoryBeanDefinitionInitializerTest {

    BeanInitializer beanInitializer;
    BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new AnnotationConfigApplicationContext(MyConfiguration.class);
        this.beanInitializer = new FactoryBeanDefinitionInitializer(new ClassBeanDefinitionInitializer(), new MethodBeanDefinitionInitializer());
    }

    @Test
    public void initBeanTest() {
        FactoryBeanDefinition factoryBeanDefinition = new FactoryBeanDefinition(
                new ClassBeanDefinition(TestFactoryBean.class, TestFactoryBean.class.getName())
        );

        CarDao carDao = (CarDao) beanInitializer.instantiate(factoryBeanDefinition, beanFactory);

        assertThat(carDao).isNotNull();
        assertThat(carDao).isInstanceOf(CarDao.class);
        assertThat(carDao.getDataSource()).isNotNull();
    }

    @Test
    public void initBeanFromMethodTest() {
        CarDao carDao = beanFactory.getBean(CarDao.class);

        assertThat(carDao).isNotNull();
        assertThat(carDao).isInstanceOf(CarDao.class);
        assertThat(carDao.getDataSource()).isNotNull();
    }
}
