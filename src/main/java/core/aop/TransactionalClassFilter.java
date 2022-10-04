package core.aop;

import core.annotation.Transactional;
import java.util.Arrays;

public class TransactionalClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        return Arrays.stream(clazz.getDeclaredMethods())
            .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }

}
