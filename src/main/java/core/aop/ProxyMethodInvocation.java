package core.aop;

import java.lang.reflect.Method;
import java.util.List;

class ProxyMethodInvocation implements MethodInvocation {

    private final Object target;
    private final Method method;
    private final Object[] args;
    private final List<Advice> advices;
    private final int index;

    ProxyMethodInvocation(Object target, Method method, Object[] args,
        List<Advice> advices, int index) {
        this.target = target;
        this.method = method;
        this.advices = advices;
        this.args = args;
        this.index = index;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.advices.size() > this.index) {
            Advice advice = this.advices.get(index);
            return advice.intercept(() -> new ProxyMethodInvocation(this.target, this.method, this.args, this.advices, this.index + 1).proceed());
        }

        return method.invoke(this.target, this.args);
    }
}
