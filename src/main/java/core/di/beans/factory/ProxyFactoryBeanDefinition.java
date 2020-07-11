package core.di.beans.factory;

import core.aop.Advice;
import org.aspectj.lang.reflect.Pointcut;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanDefinition implements BeanDefinition {

    private String name;
    private Class<?> type;
    private Advice advice;
    private Pointcut pointcut;

    public ProxyFactoryBeanDefinition(String name, Class<?> type, Advice advice, Pointcut pointcut) {
        this.name = name;
        this.type = type;
        this.advice = advice;
        this.pointcut = pointcut;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }


}
