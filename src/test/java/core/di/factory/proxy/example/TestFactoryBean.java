package core.di.factory.proxy.example;

import core.annotation.Inject;
import core.aop.FactoryBean;

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