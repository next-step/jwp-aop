package core.aop;

import java.lang.reflect.Method;

public class SayPrefixPointCut implements PointCut {
    private static final SayPrefixPointCut INSTANCE = new SayPrefixPointCut();
    private static final String SAY_PREFIX = "say";

    public static SayPrefixPointCut getInstance() {
        return INSTANCE;
    }

    private SayPrefixPointCut() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().startsWith(SAY_PREFIX);
    }
}
