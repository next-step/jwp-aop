package core.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ObjenesisCglibAopProxy<T> implements AopProxy<T> {
    private final ProxyFactoryBean<T> proxyFactoryBean;
    private Object[] constructorArgs;
    private Class<?>[] constructorArgTypes;

    public ObjenesisCglibAopProxy(ProxyFactoryBean<T> proxyFactoryBean) {
        this.proxyFactoryBean = proxyFactoryBean;
    }

    public void setConstructorArguments(Class<?>[] constructorArgTypes, Object[] constructorArgs) {
        if (ArrayUtils.isEmpty(constructorArgTypes) ||
            ArrayUtils.isEmpty(constructorArgs) ||
            constructorArgTypes.length != constructorArgs.length) {
            throw new IllegalArgumentException();
        }

        this.constructorArgTypes = constructorArgTypes;
        this.constructorArgs = constructorArgs;
    }

    @Override
    public T getProxy() {
        try {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(proxyFactoryBean.getTarget().getClass());
            enhancer.setInterceptDuringConstruction(false);

            List<Callback> callbacks = getCallbacks();

            return (T) createProxyClassAndInstance(enhancer, callbacks.toArray(new Callback[0]));
        }
        catch (CodeGenerationException | IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of " + this.advised.getTargetClass() +
                                             ": Common causes of this problem include using a final class or a non-visible class",
                                         ex);
        }
        catch (Throwable ex) {
            // TargetSource.getTarget() failed
            throw new AopConfigException("Unexpected AOP except˚ion", ex);
        }
    }

    private List<Callback> getCallbacks() {
        return proxyFactoryBean.getAdvisors().stream()
            .map(Advisor::getAdvice)
            .collect(toList());
    }

    private Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setCallbacks(callbacks);

        if (!ArrayUtils.isEmpty(constructorArgTypes) && !ArrayUtils.isEmpty(constructorArgs)) {
            return enhancer.create(constructorArgTypes, this.constructorArgs);
        }

        return enhancer.create();
    }
}
