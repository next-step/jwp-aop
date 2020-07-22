package core.di.factory.example;

import core.aop.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import study.aop.cglib.HelloTarget;
import study.aop.cglib.UpperCaseMethodInterceptor;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HelloTargetFactoryBean implements FactoryBean<HelloTarget> {

    private final Enhancer enhancer = new Enhancer();

    @Override
    public HelloTarget getObject() throws Exception {
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperCaseMethodInterceptor((m, targetClass, args) -> m.getName().startsWith("say")));
        return (HelloTarget) enhancer.create();
    }

    @Override
    public Class<? extends HelloTarget> getObjectType() {
        return HelloTarget.class;
    }
}
