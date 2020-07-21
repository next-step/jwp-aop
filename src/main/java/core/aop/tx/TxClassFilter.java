package core.aop.tx;

import core.annotation.Transactional;
import core.aop.ClassFilter;

public class TxClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class<?> clazz) {
        return clazz.isAnnotationPresent(Transactional.class);
    }

}
