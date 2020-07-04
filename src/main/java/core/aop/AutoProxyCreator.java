package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanPostProcessor;

import java.util.Collection;

/**
 * Created by yusik on 2020/07/04.
 */
public abstract class AutoProxyCreator implements BeanPostProcessor {

    protected BeanFactory beanFactory;

    @Override
    public Object postProcess(Object bean) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(bean);
        proxyFactoryBean.setPointcut(getPointcut());
        proxyFactoryBean.addAdvices(getAdvices());
        proxyFactoryBean.setBeanFactory(beanFactory);
        return proxyFactoryBean.getObject();
    }

    protected abstract Collection<Advice> getAdvices();

    public abstract Pointcut getPointcut();

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
