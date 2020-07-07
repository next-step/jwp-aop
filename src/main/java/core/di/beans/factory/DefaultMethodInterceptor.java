package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.MethodMatcher;
import core.di.beans.factory.aop.PointCut;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Optional;

public class DefaultMethodInterceptor implements MethodInterceptor {

    private final Advice advice;
    private final MethodMatcher methodMatcher;

    public DefaultMethodInterceptor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        final Optional<PointCut> maybePointCut = Optional.ofNullable(pointCut);
        if (maybePointCut.isPresent()) {
            this.methodMatcher = maybePointCut.get().getMethodMatcher();
        } else {
            this.methodMatcher = (method, targetClass) -> true;
        }
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (methodMatcher.matches(method, obj.getClass()) && advice != null) {
            return advice.invoke(obj, method, args, proxy);
        }
        return proxy.invokeSuper(obj, args);
    }
}
