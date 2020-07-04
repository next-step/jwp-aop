package core.aop;

import next.controller.TestProxyBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@DisplayName("프록시 빈 정의 클래스")
class ProxyBeanDefinitionTest {

    @Test
    @DisplayName("제네릭 타입을 잘 가져올까?")
    void getGenericType() {
        ProxyBeanDefinition proxyBeanDefinition = new ProxyBeanDefinition(TestProxyBean.class);

        assertThat(proxyBeanDefinition.getTargetClass()).isEqualTo(next.controller.Test.class);
    }

    @Test
    @DisplayName("생성자")
    void getConstructor() {
        ProxyBeanDefinition proxyBeanDefinition = new ProxyBeanDefinition(TestProxyBean.class);

        Constructor<?> injectConstructor = proxyBeanDefinition.getInjectConstructor();
        System.out.println(Arrays.toString(injectConstructor.getParameterTypes()));
    }
}
