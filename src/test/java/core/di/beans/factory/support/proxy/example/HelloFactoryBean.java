package core.di.beans.factory.support.proxy.example;

import core.di.beans.factory.proxy.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;
import study.proxy.example.cglib.MethodCallLogInterceptor;

public class HelloFactoryBean implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new MethodCallLogInterceptor());
        return (Hello) enhancer.create();
    }
}
