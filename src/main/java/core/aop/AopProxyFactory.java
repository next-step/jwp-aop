package core.aop;

public class AopProxyFactory {
    private static final int EMPTY = 0;
    private static final AopProxyFactory INSTANCE = new AopProxyFactory();

    public static AopProxyFactory getInstance() {
        return INSTANCE;
    }

    private AopProxyFactory() {
    }

    public AopProxy createAopProxy(Object target, Advisor advisor) {
        if (target.getClass().getInterfaces().length == EMPTY) {
            return new CglibAopProxy(target, advisor);
        }
        return new JdkDynamicAopProxy(target, advisor);
    }
}
