package core.aop;

import com.google.common.collect.Lists;
import core.aop.advice.ProxyMethodInvocation;
import core.aop.advisor.Advisor;
import lombok.Getter;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;

public class ProxyFactoryBean implements FactoryBean<Object> {
    @Getter
    private Object target;

    @Getter
    private List<Advisor> advisors = Lists.newArrayList();

    public ProxyFactoryBean setTarget(Object target) {
        this.target = target;
        return this;
    }

    public ProxyFactoryBean addAdvisor(Advisor advisor) {
        advisors.add(advisor);
        return this;
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new ProxyMethodInvocation(advisors));
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
