package core.aop.intercept;

import java.lang.reflect.Method;

public interface MethodInvocation extends Joinpoint {

    Method getMethod();
}
