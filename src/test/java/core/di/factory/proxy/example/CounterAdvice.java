package core.di.factory.proxy.example;

import core.aop.Advice;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class CounterAdvice implements Advice {

    private Counter counter;

    public CounterAdvice(Counter counter) {
        this.counter = counter;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        counter.addCount();
        return proxy.invokeSuper(obj, args);
    }
}
