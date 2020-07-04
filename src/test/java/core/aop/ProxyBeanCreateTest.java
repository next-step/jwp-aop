package core.aop;

import core.aop.example.di.ProxyClass;
import core.aop.example.di.TargetClass;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프록시 빈 생성 테스트")
public class ProxyBeanCreateTest {
    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("core.aop.example.di");
        beanFactory.preInstantiateSingletons();
    }

    @Test
    @DisplayName("빈을 필요로하는 생성자가 있는 경우 초기화가 잘 되는지")
    void initBean() {
        assertThat(beanFactory.getBean(ProxyClass.class)).isNull();
        assertThat(beanFactory.getBean(TargetClass.class)).isNotNull();
        assertThat(beanFactory.getBean(TargetClass.class).someComponent()).isNotNull();
    }

    @Test
    @DisplayName("AOP 가 잘 동작하는지")
    void checkAOP() {
        TargetClass bean = beanFactory.getBean(TargetClass.class);

        assertThat(bean.getString()).isEqualTo("STRING");
        assertThat(bean.stringValue()).isEqualTo("string");
    }
}
