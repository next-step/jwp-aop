package next.dao;

import core.annotation.Component;
import core.aop.ProxyFactoryBean;
import core.aop.advice.StopWatchAdvice;
import core.di.beans.factory.config.BeanDefinition;

@Component
public class UserDaoProxy extends ProxyFactoryBean<UserDao> {

    public UserDaoProxy(BeanDefinition beanDefinition, Object... arguments) {
        super(
                beanDefinition,
                arguments,
                new StopWatchAdvice(),
                (method, targetClass, args) -> method.getName().startsWith("find")
        );
    }
}
