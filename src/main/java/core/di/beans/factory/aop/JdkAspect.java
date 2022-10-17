package core.di.beans.factory.aop;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class JdkAspect extends Aspect implements InvocationHandler {

    private final Target target;
    private final Advice advice;
    private final Map<String, Method> methods = Maps.newHashMap();

    public JdkAspect(PointCut pointCut, Target target, Advice advice) {
        super(pointCut);
        this.target = target;
        this.advice = advice;
        for (Method method : target.declaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = methods.get(method.getName()).invoke(target.getObject(), args);
        if (matches(method, proxy.getClass(), args)) {
            return advice.intercept(object);
        }
        return object;
    }
}
