package study.aop;

import core.annotation.Component;
import core.aop.MethodMatcherUpperCaseStartsWith;
import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;

@Component
public class HelloFactoryBean implements FactoryBean<Hello> {

    public HelloFactoryBean() {
    }

    @Override
    public Hello getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloFactoryBeanTarget.class);
        enhancer.setCallback(new HelloTargetCglibProxy(new MethodMatcherUpperCaseStartsWith()));

        return (HelloFactoryBeanTarget) enhancer.create();
    }

    @Override
    public Class<? extends Hello> getObjectType() {
        return Hello.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
