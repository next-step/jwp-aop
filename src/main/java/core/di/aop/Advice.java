package core.di.aop;

import java.lang.reflect.Method;

public interface Advice {

    Object invoke(final Object object, final Method method, final Object[] args);

}
