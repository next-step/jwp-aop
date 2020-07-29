package core.di.beans.factory.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyFactoryBean implements FactoryBean<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);

    private Class<?> target;

    ProxyFactoryBeanAdvisor proxyFactoryBeanAdvisor;


    private MethodMatcher pointcut;

    public ProxyFactoryBean() {
        proxyFactoryBeanAdvisor = new ProxyFactoryBeanAdvisor();

        pointcut = new AllMethodMatcher();
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallbacks(proxyFactoryBeanAdvisor.convertAdviceListToArray());
        enhancer.setCallbackFilter(method -> pointcut.matches(method, target) ? 1 : 0);

        return enhancer.create();
    }

    public void setTarget(Class<?> target) {
        this.target = target;
    }

    public void addAdvice(MethodInterceptor advice) {
        proxyFactoryBeanAdvisor.addAdvice(advice);
    }

    public void setPointcut(MethodMatcher methodMatcher) {
        pointcut = methodMatcher;
    }

}
