package core.di.factory.example3;

import core.annotation.Inject;
import core.di.beans.factory.FactoryBean;
import core.di.factory.FactoryBeanDefinitionInitializerTest;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
public class TestFactoryBean implements FactoryBean<CarDao> {

    private DataSource dataSource;

    @Inject
    public TestFactoryBean(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CarDao getObject() throws Exception {
        return new CarDao(dataSource);
    }
}