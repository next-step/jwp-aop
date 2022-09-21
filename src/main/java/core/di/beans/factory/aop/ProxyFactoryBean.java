package core.di.beans.factory.aop;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.aop.advisor.Target;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ProxyFactoryBean implements FactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);

    private Target target;
    private Aspect aspect;
    private BeanFactory beanFactory;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.type());
        enhancer.setCallbackFilter(method -> aspect.matches(method, target));
        enhancer.setCallbacks(aspect.toArrayAdvice());

        Class<?>[] types = target.parameterTypes();
        return enhancer.create(types, extractInjectFields(types));
    }

    @Override
    public Class<?> getObjectType() {
        return target.type();
    }

    private Object[] extractInjectFields(Class<?>[] types) {
        return Arrays.stream(types)
                .map(type-> beanFactory.getBean(type))
                .toArray();
    }
}
