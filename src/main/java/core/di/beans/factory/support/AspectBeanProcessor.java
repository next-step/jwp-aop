package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AspectBeanProcessor {

    private List<AspectBean> aspectBeans;

    public AspectBeanProcessor(List<AspectBean> aspectBeans) {
        this.aspectBeans = aspectBeans;
    }

    public boolean isAvailableMethods(BeanDefinition beanDefinition, Method method) {
        for (AspectBean aspectBean : aspectBeans) {
            if (aspectBean.isAspect(beanDefinition, method)) {
                return true;
            }
        }
        return false;
    }

    public Object process(BeanDefinition beanDefinition, ProxyInvocation invocation) {
        List<AspectBean> aspectInvocations = new ArrayList<>();
        for (AspectBean aspectBean : aspectBeans) {
            if (aspectBean.isAspect(beanDefinition, invocation.getMethod())) {
                aspectInvocations.add(aspectBean);
            }
        }

        return new AspectInvocationChain(aspectInvocations).invoke(invocation);
    }

    public boolean isAspect(BeanDefinition beanDefinition) {
        for (AspectBean aspectBean : aspectBeans) {
            if (aspectBean.isAspect(beanDefinition)) {
                return true;
            }
        }

        return false;
    }

}
