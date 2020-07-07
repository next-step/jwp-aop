package core.aop;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.aop.Advisor;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    @Getter
    private T target;

    @Getter
    private List<Class<?>> interfaces = Lists.newArrayList();

    @Getter
    private List<Advisor> advisors = Lists.newArrayList();

    public ProxyFactoryBean<T> setInterface(Class<?> targetInterface) {
        this.interfaces.add(targetInterface);
        return this;
    }

    public ProxyFactoryBean<T> setTarget(T target) {
        this.target = target;
        return this;
    }

    public ProxyFactoryBean<T> addAdvisor(Advisor advisor) {
        advisors.add(advisor);
        return this;
    }

    @Override
    public T getObject() throws Exception {
        if (CollectionUtils.isEmpty(interfaces)) {
            new ObjenesisCglibAopProxy(this).getProxy();
        }

        return (T) new JdkDynamicAopProxy<T>(this).getProxy();
    }
}
