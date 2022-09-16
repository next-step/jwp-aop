package core.di.aop;

import java.lang.reflect.Method;

public class UppercaseAdvice implements Advice {

    @Override
    public Object invoke(final Object object, final Method method, final Object[] args) throws Throwable {
        return method.invoke(object, args).toString().toUpperCase();
    }

    private UppercaseAdvice() {
    }

    public static UppercaseAdvice getInstance() {
        return UppercaseAdviceHolder.INSTANCE;
    }

    private static class UppercaseAdviceHolder {
        private static final UppercaseAdvice INSTANCE = new UppercaseAdvice();
    }
}
