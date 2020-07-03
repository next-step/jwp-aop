package core.aop;

/**
 * Created by yusik on 2020/07/03.
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
