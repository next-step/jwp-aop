package core.di.beans.factory.proxy;

import com.google.common.collect.Lists;
import core.di.beans.factory.config.BeanDefinition;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cglib.core.ClassLoaderAwareGeneratorStrategy;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private Pointcut pointCut;
    private Advice advice;
    private Object target;

    private List<Advisor> advisors = Lists.newArrayList();

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    private Class<?> createEnhancedSubclass(BeanDefinition beanDefinition) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setCallbackFilter(new CglibSubclassingInstantiationStrategy.MethodOverrideCallbackFilter(beanDefinition));
        enhancer.setCallbackTypes(CALLBACK_TYPES);
        return enhancer.createClass();
    }
}
