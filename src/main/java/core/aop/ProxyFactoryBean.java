package core.aop;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private final Enhancer enhancer = new Enhancer();
    private Object target;

    public ProxyFactoryBean(Object target) {
        this.target = target;
        enhancer.setSuperclass(target.getClass());
    }

    public void addCallbacks(Callback... callbacks) {
        enhancer.setCallbacks(callbacks);
    }

    @Override
    public Object getObject() {
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

}
