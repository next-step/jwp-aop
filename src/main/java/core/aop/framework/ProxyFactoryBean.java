package core.aop.framework;

import java.lang.reflect.Proxy;

import org.springframework.util.ClassUtils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import core.aop.FactoryBean;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object getObject() {
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(target.getClass());
        if (interfaces.length == 0) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallback((MethodInterceptor)(obj, method, args, proxy) -> {
                String result = String.valueOf(proxy.invoke(target, args));
                String methodName = method.getName();
                if (methodName.startsWith("say")) {
                    return result.toUpperCase();
                }
                return result;
            });
            return enhancer.create();
        }

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, (proxy, method, args) -> {
            String result = String.valueOf(method.invoke(target, args));
            String methodName = method.getName();
            if (methodName.startsWith("say")) {
                return result.toUpperCase();
            }
            return result;
        });
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
