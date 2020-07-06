package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.PointCut;
import core.di.beans.factory.support.BeanFactoryUtils;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * todo: 우선 cglib
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object target;
    private Advice advice;
    private PointCut pointCut;
    private BeanFactory beanFactory;
    private Object instance;

    @Override
    public Object getObject() throws Exception {
        if (isSingleton() && instance != null) {
            return instance;
        }
        instance = instantiate();
        return instance;
    }

    private Object instantiate() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new DefaultMethodInterceptor(advice, pointCut));
        final Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(getObjectType());
        if (injectedConstructor != null) {
            final Class<?>[] types = injectedConstructor.getParameterTypes();
            final Object[] deps = Arrays.stream(types)
                    .map(clazz -> beanFactory.getBean(clazz))
                    .toArray();
            return enhancer.create(types, deps);
        }
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setPointCut(PointCut pointCut) {
        this.pointCut = pointCut;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
