package core.di.beans.factory.aop;

import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyFactoryBean implements FactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);

    private Target target;
    private Aspect aspect;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.type());
        enhancer.setCallbackFilter(method -> aspect.matches(method, target));
        enhancer.setCallbacks(aspect.toArrayAdvice());

        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.type();
    }
}
