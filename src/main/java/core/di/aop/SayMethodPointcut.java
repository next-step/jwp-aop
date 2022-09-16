package core.di.aop;

import java.lang.reflect.Method;

public class SayMethodPointcut implements Pointcut {

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return method.getName().startsWith("say");
    }

    private SayMethodPointcut(){
    }

    public static SayMethodPointcut getInstance() {
        return SayMethodPointcutHolder.INSTANCE;
    }

    private static class SayMethodPointcutHolder {
        private static final SayMethodPointcut INSTANCE = new SayMethodPointcut();
    }
}
