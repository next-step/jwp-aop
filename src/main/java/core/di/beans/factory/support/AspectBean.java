package core.di.beans.factory.support;

import core.annotation.Around;
import core.annotation.Ordered;
import core.di.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AspectBean {

    private Object bean;
    private Method around;
    private String pattern;
    private int order = Integer.MAX_VALUE;

    public static AspectBean of(Object bean) {
        AspectBean aspectBean = new AspectBean();
        aspectBean.setBean(bean);

        if (bean.getClass().isAnnotationPresent(Ordered.class)) {
            final Ordered ordered = bean.getClass().getAnnotation(Ordered.class);
            aspectBean.setOrder(ordered.order());
        }

        final Method aroundMethod = Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> !Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.isAnnotationPresent(Around.class))
                .findFirst()
                .orElse(null);

        if (aroundMethod != null) {
            aspectBean.setAround(aroundMethod);
            aspectBean.setPattern(aroundMethod
                    .getAnnotation(Around.class)
                    .pointcut());
        }

        return aspectBean;
    }

    private AspectBean() {}

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    public void setOrder(int order) {
        this.order = order;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setAround(Method around) {
        this.around = around;
    }

    public int getOrder() {
        return order;
    }

    public Object invoke(ProxyInvocation invocation, AspectInvocationChain chain) {
        try {
            invocation.setChain(chain);
            return around.invoke(bean, invocation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAspect(BeanDefinition beanDefinition) {
        if (around == null) {
            return false;
        }

        return AntMatcherUtils.matches(pattern, beanDefinition.getBeanClass().getName());
    }

    public boolean isAspect(BeanDefinition beanDefinition, Method method) {
        if (around == null) {
            return false;
        }

        return AntMatcherUtils.matches(pattern,
                AntMatcherUtils.fullMethodName(beanDefinition.getBeanClass(), method));
    }
}
