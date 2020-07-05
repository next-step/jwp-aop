package core.aop.exception;

public class ProxyBeanCreateException extends RuntimeException {
    public ProxyBeanCreateException(Class<?> clazz) {
        super("Fail to create proxy bean of " + clazz);
    }

    public ProxyBeanCreateException(Class<?> clazz, Exception e) {
        super("Fail to create proxy bean of " + clazz + " cuz : " + e.getMessage());
    }
}
