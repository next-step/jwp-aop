package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;

import java.util.List;

public class BeanDefinitionUtils {

    public static void aspectProcess(List<AspectBean> aspectBeans, BeanDefinition beanDefinition) {
        for (AspectBean aspectBean : aspectBeans) {
            if (aspectBean.isAspect(beanDefinition)) {
                beanDefinition.addAspect(aspectBean);
            }
        }
    }

}
