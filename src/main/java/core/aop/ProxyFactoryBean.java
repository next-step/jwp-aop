package core.aop;

import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private final Enhancer enhancer = new Enhancer();
    private final Object target;
    private final List<Advisor> advisors;

    public ProxyFactoryBean(Object target, List<Advisor> advisors) {
        this.target = target;
        this.advisors = advisors;
    }

    @Override
    public Object getObject() throws Exception {
        Method[] declaredMethods = target.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            for (Advisor advisor : advisors) {
                if (advisor.fitPointCut(declaredMethod, target, declaredMethod.getParameterTypes())) {
                    enhancer.setSuperclass(target.getClass());
                    enhancer.setCallback(advisor.getAdvice());
                    return enhancer.create();
                }
            }
        }

        return target;
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

}
