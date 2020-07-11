package core.di.factory.proxy.example;

import core.aop.Advice;
import core.aop.BeanInterceptor;
import core.aop.Pointcut;
import core.di.beans.factory.BeanDefinition;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.ProxyFactoryBean;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class CarDaoProxyFactoryBean extends ProxyFactoryBean<CarDao> {

    public CarDaoProxyFactoryBean() {
        super(CarDao.class, method -> true, new CounterAdvice(new Counter()));
    }

}


