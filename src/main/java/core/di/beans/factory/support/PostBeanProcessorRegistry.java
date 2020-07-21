package core.di.beans.factory.support;

import core.aop.factorybean.TxProxyFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class PostBeanProcessorRegistry {

    private List<PostBeanProcessor> processors = new ArrayList<>();

    public PostBeanProcessorRegistry() {
        processors.add((clazz, bean) -> new TxProxyFactoryBean(bean, clazz).getObject());
    }

    public void addProcessor(PostBeanProcessor postBeanProcessor) {
        this.processors.add(postBeanProcessor);
    }

    public Object process(Class<?> clazz, Object bean) {
        Object processedBean = bean;
        for (PostBeanProcessor processor : processors) {
            processedBean = processor.process(clazz, processedBean);
        }

        return processedBean;
    }

}
