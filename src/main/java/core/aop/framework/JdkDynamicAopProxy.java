package core.aop.framework;

import java.lang.reflect.Proxy;

import org.springframework.util.ClassUtils;

public class JdkDynamicAopProxy implements AopProxy {

    private final Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getProxy() {
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(target.getClass());
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, (proxy, method, args) -> {
            String result = String.valueOf(method.invoke(target, args));
            String methodName = method.getName();
            if (methodName.startsWith("say")) {
                return result.toUpperCase();
            }
            return result;
        });
    }
}
