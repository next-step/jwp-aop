package core.di.beans.factory;

import core.aop.Advice;
import core.aop.Pointcut;


/**
 * @author KingCjy
 */
public class ProxyFactoryBeanDefinition implements BeanDefinition {

    private String name;
    private Class<?> type;
    private Pointcut pointcut;
    private Advice advice;

    public ProxyFactoryBeanDefinition(String name, Class<?> type, Pointcut pointcut, Advice advice) {
        this.name = name;
        this.type = type;
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public Advice getAdvice() {
        return advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
