package core.di.context.annotation;

import core.di.beans.factory.support.FactoryBean;

import java.lang.reflect.Method;

public class BeanDefinitionSupport {

    protected Class<?> resolveBeanNameClass(Class<?> clazz) {
        if (FactoryBean.class.isAssignableFrom(clazz)) {
            try {
                final Method method = clazz.getMethod("getObject");

                if (method != null) {
                    return method.getReturnType();
                }

                return clazz;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return clazz;
    }

}
