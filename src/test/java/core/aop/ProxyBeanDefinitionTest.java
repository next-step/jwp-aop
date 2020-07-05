package core.aop;

import core.aop.example.Proxy;
import core.aop.example.SimpleTarget;
import core.di.beans.factory.config.BeanDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프록시 빈 정의 클래스")
class ProxyBeanDefinitionTest {
    private ProxyBeanDefinition proxyBeanDefinition;

    @BeforeEach
    void setEnv() {
        proxyBeanDefinition = new ProxyBeanDefinition(Proxy.class);
    }

    @Test
    @DisplayName("제네릭 타입을 잘 가져올까?")
    void getGenericType() {
        assertThat(proxyBeanDefinition.getTargetClass()).isEqualTo(SimpleTarget.class);
    }

    @Test
    @DisplayName("생성자")
    void getConstructor() {
        Constructor<?> injectConstructor = proxyBeanDefinition.getInjectConstructor();

        assertThat(injectConstructor.getParameterTypes()).hasSize(2);
        assertThat(injectConstructor.getParameterTypes()[0]).isAssignableFrom(BeanDefinition.class);
        assertThat(injectConstructor.getParameterTypes()[1]).isInstanceOf(Class.class); //vararg
    }

    @Test
    @DisplayName("타겟 빈 정의 클래스")
    void getTargetBeanDefinition() {
        BeanDefinition targetBeanDefinition = proxyBeanDefinition.getTargetBeanDefinition();

        assertThat(targetBeanDefinition.getBeanClass()).isEqualTo(SimpleTarget.class);
    }

    @Test
    @DisplayName("타겟 빈 생성자 파라미터")
    void getTargetConstructorParameterTypes() {
        Class<?>[] targetConstructorParameterTypes = proxyBeanDefinition.getTargetConstructorParameterTypes();

        assertThat(targetConstructorParameterTypes).isEmpty();
    }
}
