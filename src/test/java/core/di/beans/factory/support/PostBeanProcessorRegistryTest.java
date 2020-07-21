package core.di.beans.factory.support;

import core.aop.factorybean.ProxyFactoryBean;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;

import static org.assertj.core.api.Assertions.assertThat;

class PostBeanProcessorRegistryTest {

    @DisplayName("Bean instance 에 Proxy 적용하기")
    @Test
    void process() {
        /* given */
        PostBeanProcessorRegistry postBeanProcessorRegistry = new PostBeanProcessorRegistry();
        postBeanProcessorRegistry.addProcessor((clazz, bean) -> new UppercaseProxyFactoryBean(bean, clazz).getObject());
        postBeanProcessorRegistry.addProcessor((clazz, bean) -> new QuestionMarkProxyFactoryBean(bean, clazz).getObject());

        /* when */
        Hello processedBean = (Hello) postBeanProcessorRegistry.process(Hello.class, new HelloBean());

        /* then */
        assertThat(processedBean).isNotNull();
        assertThat(processedBean.sayHello("nick")).isEqualTo("HELLO NICK?");
    }

    @RequiredArgsConstructor
    public static class UppercaseProxyFactoryBean extends ProxyFactoryBean {

        private final Object target;
        private final Class<?> objectType;

        @Override
        public Class<?> getObjectType() {
            return objectType;
        }

        @Override
        protected InvocationHandler getInvocationHandler() {
            return (proxy, method, args) -> {
                String result = (String) method.invoke(target, args);
                return result.toUpperCase();
            };
        }

    }

    @RequiredArgsConstructor
    public static class QuestionMarkProxyFactoryBean extends ProxyFactoryBean {

        private final Object target;
        private final Class<?> objectType;

        @Override
        public Class<?> getObjectType() {
            return objectType;
        }

        @Override
        protected InvocationHandler getInvocationHandler() {
            return (proxy, method, args) -> {
                String result = (String) method.invoke(target, args);
                return result + "?";
            };
        }

    }

    public static class HelloBean implements Hello {

        @Override
        public String sayHello(String name) {
            return "hello " + name;
        }

    }

    public interface Hello {

        String sayHello(String name);

    }

}
