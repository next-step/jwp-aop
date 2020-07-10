package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.FactoryBean;

/**
 * @author KingCjy
 */
@Component
public class ExampleFactoryBean implements FactoryBean<ExampleService> {
    @Override
    public ExampleService getObject() throws Exception {
        return new ExampleService("Hello World");
    }
}
