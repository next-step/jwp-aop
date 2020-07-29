package core.di.beans.factory.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBeanAdvisor {

    private final List<MethodInterceptor> advice;

    public ProxyFactoryBeanAdvisor() {
        advice = new ArrayList<>();
        advice.add(new NoAdvice());
    }

    public void addAdvice(MethodInterceptor methodInterceptor) {
        advice.add(methodInterceptor);
    }

    public MethodInterceptor[] convertAdviceListToArray() {
        MethodInterceptor[] arrayMethodInterceptors = new MethodInterceptor[advice.size()];
        return advice.toArray(arrayMethodInterceptors);
    }
}
