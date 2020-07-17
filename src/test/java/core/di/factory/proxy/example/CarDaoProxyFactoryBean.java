package core.di.factory.proxy.example;

import core.aop.ProxyFactoryBean;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
public class CarDaoProxyFactoryBean extends ProxyFactoryBean<CarDao> {

    public CarDaoProxyFactoryBean(DataSource dataSource) {
        super(new CarDao(dataSource), method -> true, new CounterAdvice(new Counter()));
    }
}
