package core.di.beans.factory.aop;

import java.lang.reflect.Method;

public class Target {

    private final Class<?> clazz;
    private final Object object;

    public Target(Class<?> clazz, Object object) {
        this.clazz = clazz;
        this.object = object;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getObject() {
        return object;
    }

    public Method[] declaredMethods() {
        return object.getClass().getDeclaredMethods();
    }
}