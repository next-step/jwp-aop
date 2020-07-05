package core.aop;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.InjectType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public class ProxyBeanDefinition implements BeanDefinition {
    private final Class<?> proxyClass;
    private final Class<?> targetClass;
    private final Advice advice;
    private final PointCut pointCut;
    private final BeanDefinition targetBeanDefinition;

    public ProxyBeanDefinition(Class<?> clazz) {
        this(clazz, null, null);
    }

    public ProxyBeanDefinition(Class<?> clazz, Advice advice, PointCut pointCut) {
        this.proxyClass = clazz;
        if (advice != null) {
            this.targetClass = clazz;
        } else {
            this.targetClass = (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        this.targetBeanDefinition = new DefaultBeanDefinition(targetClass);
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public Constructor<?> getInjectConstructor() {
        if (advice != null) {
            return ProxyFactoryBean.class.getDeclaredConstructors()[0];
        }

        Constructor<?>[] declaredConstructors = proxyClass.getDeclaredConstructors();

        return declaredConstructors[0];
    }

    @Override
    public Set<Field> getInjectFields() {
        return targetBeanDefinition.getInjectFields();
    }

    @Override
    public Class<?> getBeanClass() {
        return targetBeanDefinition.getBeanClass();
    }

    @Override
    public InjectType getResolvedInjectMode() {
        return InjectType.INJECT_CONSTRUCTOR;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?>[] getTargetConstructorParameterTypes() {
        if (targetBeanDefinition.getInjectConstructor() == null) {
            return new Class<?>[0];
        }

        return targetBeanDefinition.getInjectConstructor().getParameterTypes();
    }

    public BeanDefinition getTargetBeanDefinition() {
        return targetBeanDefinition;
    }

    public Advice getAdvice() {
        return advice;
    }

    public PointCut getPointCut() {
        return pointCut;
    }
}
