package core.aop;

import java.lang.reflect.Method;

public class SayPointCut implements Pointcut {

    private static final SayPointCut INSTANCE = new SayPointCut();

    private SayPointCut(){}

    public static SayPointCut getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean matches(Method method, Class<?> target) {
        return method.getName().startsWith("say");
    }
}
