package core.di.beans.factory;

import core.di.beans.factory.support.BeanGettable;

import java.util.Set;

public interface BeanFactory extends BeanGettable {
    Set<Class<?>> getBeanClasses();
    void clear();
}