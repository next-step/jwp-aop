package study.cglib;

import core.di.beans.factory.FactoryBean;
import core.di.beans.proxy.UpperAfterAdvice;
import net.sf.cglib.proxy.Enhancer;
import study.dynamicProxy.Hello;
import study.dynamicProxy.HelloTarget;

public class HelloFactoryBean implements FactoryBean<Hello> {
	@Override
	public HelloTarget getObject() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloTarget.class);
		enhancer.setCallback(new UpperAfterAdvice((method) -> method.getName().startsWith("say")));
		return (HelloTarget) enhancer.create();
	}

	@Override
	public Class<HelloTarget> getObjectType() {
		return HelloTarget.class;
	}
}
