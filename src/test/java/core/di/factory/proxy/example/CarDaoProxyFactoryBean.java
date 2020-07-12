package core.di.factory.proxy.example;

import core.aop.ProxyFactoryBean;

/**
 * @author KingCjy
 */
public class CarDaoProxyFactoryBean extends ProxyFactoryBean<CarDao> {

    public CarDaoProxyFactoryBean() {
        super(CarDao.class, method -> true, new CounterAdvice(new Counter()));
    }
}
