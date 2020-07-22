package core.di.beans.factory.support;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class PostBeanProcessorRegistry {

    private List<PostBeanProcessor> processors = new ArrayList<>();

    public void addProcessor(PostBeanProcessor postBeanProcessor) {
        this.processors.add(postBeanProcessor);
    }

    public Object process(Class<?> clazz, Object bean) {
        Object processedBean = bean;
        for (PostBeanProcessor processor : processors) {
            if (processor.supports(clazz, bean)) {
                processedBean = processor.process(clazz, processedBean);
            }
        }

        return processedBean;
    }

}
