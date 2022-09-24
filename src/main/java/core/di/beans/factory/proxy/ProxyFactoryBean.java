package core.di.beans.factory.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class ProxyFactoryBean<T> implements FactoryBean<Object> {

	private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);
	private final Enhancer enhancer = new Enhancer();
	private final Class<T> target;

	public ProxyFactoryBean(Class<T> target) {
		this.target = target;
	}

	@Override
	public Object getObject() throws Exception {
		enhancer.setSuperclass(target);
		enhancer.setCallback(NoOp.INSTANCE);
		return enhancer.create();
	}

	@Override
	public Class<?> getObjectType() {
		return (Class<? extends T>) target.getClass();
	}
}
