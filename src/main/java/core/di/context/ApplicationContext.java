package core.di.context;

import core.di.beans.factory.support.BeanGettable;

import java.util.Set;

public interface ApplicationContext extends BeanGettable {
    Set<Class<?>> getBeanClasses();
}