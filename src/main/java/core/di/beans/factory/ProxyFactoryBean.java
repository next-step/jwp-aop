package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.PointCut;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * todo: 우선 cglib
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object target;
    private Advice advice;
    private PointCut pointCut;

    @Override
    public Object getObject() throws Exception {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (advice == null) {
                return proxy.invokeSuper(obj, args);
            }

            if (pointCut == null) {
                return advice.invoke(obj, method, args, proxy);
            }

            if (pointCut.getMethodMatcher().matches(method, getObjectType())) {
                return advice.invoke(obj, method, args, proxy);
            }

            return proxy.invokeSuper(obj, args);
        });
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setPointCut(PointCut pointCut) {
        this.pointCut = pointCut;
    }
}
