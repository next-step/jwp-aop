package core.di.factory;

import core.di.beans.factory.BeanFactory;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.proxy.example.CarDao;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanInitTest {

    private BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

    @Test
    public void initBeanTest() {
        CarDao carDao = beanFactory.getBean("myCarDao", CarDao.class);

        assertThat(carDao).isNotNull();
        assertThat(carDao.getDataSource()).isNotNull();
    }
}
