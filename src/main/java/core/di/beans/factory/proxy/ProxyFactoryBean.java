package core.di.beans.factory.proxy;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

	private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);
	private final Enhancer enhancer = new Enhancer();
	private final Class<?> target;
	private Advice advice;

	public ProxyFactoryBean(Class<?> target) {
		this.target = target;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	@Override
	public Object getObject() {
		enhancer.setSuperclass(target);
		if (!Objects.isNull(advice)) {
			enhancer.setCallback(advice);
		}
		return enhancer.create();
	}

	@Override
	public Class<?> getObjectType() {
		return target.getClass();
	}
}
