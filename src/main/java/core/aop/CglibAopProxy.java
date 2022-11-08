package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements Proxy{

    private final Class<?> targetClass;
    private final Object targetSource;
    private final Advisor advisor;

    public CglibAopProxy(Class<?> targetClass, Object targetSource, Advisor advisor) {
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(
                (MethodInterceptor) (obj, method, args, proxy) -> {
                    if (advisor.matches(targetClass, method)) {
                        return advisor.invoke(() -> method.invoke(targetSource, args));
                    }
                    return method.invoke(targetSource, args);
                }
        );

        Class<?>[] parameterTypes = parameterTypes();
        return enhancer.create(parameterTypes, new Object[parameterTypes.length]);
    }

    private Class<?>[] parameterTypes() {
        return targetClass.getDeclaredConstructors()[0]
                .getParameterTypes();
    }
}
