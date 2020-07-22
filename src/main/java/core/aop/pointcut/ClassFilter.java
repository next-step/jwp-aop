package core.aop.pointcut;

public interface ClassFilter {

    boolean matches(Class<?> clazz);

}
