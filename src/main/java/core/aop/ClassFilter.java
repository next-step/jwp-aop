package core.aop;

@FunctionalInterface
public interface ClassFilter {

    boolean matches(Class<?> clazz);

}
