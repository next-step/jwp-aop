package core.aop.example;

import core.aop.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import study.proxy.HelloMethodMatcher;
import study.proxy.cglib.HelloMethodInterceptor;

public class TestTargetFactoryBean implements FactoryBean<TestTarget> {

    private final Enhancer enhancer = new Enhancer();

    @Override
    public TestTarget getObject() {
        enhancer.setSuperclass(TestTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new TestTarget(), new HelloMethodMatcher("say")));
        return (TestTarget) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return TestTarget.class;
    }
}
