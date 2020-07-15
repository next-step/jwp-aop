package core.aop;

import com.google.common.collect.Lists;
import core.aop.advice.ProxyMethodInterceptor;
import core.aop.advisor.Advisor;
import lombok.Getter;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    @Getter
    private T target;

    @Getter
    private List<Advisor> advisors = Lists.newArrayList();

    public ProxyFactoryBean setTarget(T target) {
        this.target = target;
        return this;
    }

    public ProxyFactoryBean addAdvisor(Advisor advisor) {
        advisors.add(advisor);
        return this;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new ProxyMethodInterceptor(advisors));
        return (T) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
