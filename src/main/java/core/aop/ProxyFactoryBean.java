package core.aop;

import com.google.common.collect.Lists;
import core.aop.advice.ProxyMethodInterceptor;
import core.aop.advisor.Advisor;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.DefaultBeanFactory;
import lombok.Getter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;


public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private final BeanFactory beanFactory;

    @Getter
    private final T target;

    @Getter
    private List<Advisor> advisors = Lists.newArrayList();

    public ProxyFactoryBean(T target) {
        this.target = target;
        this.beanFactory = null;
    }

    public ProxyFactoryBean(T target, BeanFactory beanFactory) {
        this.target = target;
        this.beanFactory = beanFactory;
    }

    public ProxyFactoryBean<T> addAdvisor(Advisor advisor) {
        advisors.add(advisor);
        return this;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new ProxyMethodInterceptor(advisors));

        if (Objects.nonNull(beanFactory)) {
            Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(getObjectType());

            if (injectedConstructor != null) {
                return createWithConstructor(enhancer, injectedConstructor);
            }
        }

        return (T) enhancer.create();
    }

    private T createWithConstructor(Enhancer enhancer, Constructor<?> injectedConstructor) {
        return (T) enhancer.create(
            injectedConstructor.getParameterTypes(),
           BeanFactoryUtils.getArguments(beanFactory, injectedConstructor.getParameterTypes())
        );
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
