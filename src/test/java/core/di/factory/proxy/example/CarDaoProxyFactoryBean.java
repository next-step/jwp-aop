package core.di.factory.proxy.example;

import core.annotation.Inject;
import core.aop.ProxyFactoryBean;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
public class CarDaoProxyFactoryBean extends ProxyFactoryBean<CarDao> {

    @Inject
    private DataSource dataSource;

    public CarDaoProxyFactoryBean() {
        setTarget(new CarDao(dataSource));
        setPointcut(method -> true);
        setAdvice(new CounterAdvice(new Counter()));
    }
}
