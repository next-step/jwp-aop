package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.util.Assert;

final class CglibAopProxy implements AopProxy {

    private final Class<?> targetClass;
    private final Object targetSource;
    private final Advisor advisor;

    private CglibAopProxy(Class<?> targetClass, Object targetSource, Advisor advisor) {
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(advisor, "'advisor' must not be null");
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    static CglibAopProxy of(Class<?> targetClass, Object targetSource, Advisor advisor) {
        return new CglibAopProxy(targetClass, targetSource, advisor);
    }

    @Override
    public Object proxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (advisor.matches(targetClass) || advisor.matches(method)) {
                return advisor.invoke(() -> method.invoke(targetSource, args));
            }
            return method.invoke(targetSource, args);
        });

        Class<?>[] parameterTypes = parameterTypes();
        return enhancer.create(parameterTypes, new Object[parameterTypes.length]);
    }

    private Class<?>[] parameterTypes() {
        return targetClass.getDeclaredConstructors()[0]
                .getParameterTypes();
    }
}
