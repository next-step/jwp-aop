package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.PointCut;
import net.sf.cglib.proxy.Enhancer;

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
        enhancer.setCallback(new DefaultMethodInterceptor(advice, pointCut));
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
