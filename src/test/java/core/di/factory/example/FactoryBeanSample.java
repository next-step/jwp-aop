package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.support.FactoryBean;
import next.model.User;

@Component
public class FactoryBeanSample implements FactoryBean<User> {

    @Override
    public User getObject() {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

}
