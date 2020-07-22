package core.di.factory.example.proxy;

import core.aop.factorybean.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import study.proxy.cglib.UppercaseInterceptor;
import core.aop.pointcut.MethodMatcher;
import study.proxy.matcher.SayMethodMatcher;

public class ProxyTargetBeanFactory implements FactoryBean<ProxyTargetBean> {

    private final Enhancer enhancer = new Enhancer();

    @Override
    public ProxyTargetBean getObject() {
        MethodMatcher methodMatcher = new SayMethodMatcher();

        enhancer.setSuperclass(ProxyTargetBean.class);
        enhancer.setCallback(new UppercaseInterceptor(methodMatcher));

        return (ProxyTargetBean) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return ProxyTargetBean.class;
    }

}
