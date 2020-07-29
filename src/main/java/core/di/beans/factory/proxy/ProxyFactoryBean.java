package core.di.beans.factory.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean implements FactoryBean<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);

    private Class<?> target;

    private List<MethodInterceptor> advice;

    private MethodMatcher pointcut;

    public ProxyFactoryBean() {
        advice = new ArrayList<>();
        advice.add(new NoAdvice());
        pointcut = new AllMethodMatcher();
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallbacks(convertAdviceListToArray());
        enhancer.setCallbackFilter(method -> pointcut.matches(method, target) ? 1 : 0);

        return enhancer.create();
    }

    public void setTarget(Class<?> target) {
        this.target = target;
    }

    public void addAdvice(MethodInterceptor methodInterceptor) {
        advice.add(methodInterceptor);
    }

    public void setPointcut(MethodMatcher methodMatcher) {
        pointcut = methodMatcher;
    }

    private MethodInterceptor[] convertAdviceListToArray() {
        MethodInterceptor[] arrayMethodInterceptors = new MethodInterceptor[advice.size()];
        return advice.toArray(arrayMethodInterceptors);
    }
}
