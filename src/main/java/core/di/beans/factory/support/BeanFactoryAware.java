package core.di.beans.factory.support;

import core.di.beans.factory.BeanFactory;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory);
}