package core.beandefinition;

import core.aop.Advice;
import core.aop.PointCut;
import core.aop.ProxyBeanDefinition;
import core.beandefinition.example.HigherClass;
import core.beandefinition.example.LowerClass;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.DefaultBeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("타겟 클래스가 필드 인젝션을 받는 프록시빈 생성 테스트")
public class ProxyBeanOfFieldInjectTargetCreateTest {
    private DefaultBeanFactory beanFactory;
    private Advice advice = (object, method, arguments, proxy) -> {
        try {
            return proxy.invokeSuper(object, arguments);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    };
    private PointCut pointCut = (method, targetClass, arguments) -> true;


    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        beanFactory.registerBeanDefinition(LowerClass.class, new DefaultBeanDefinition(LowerClass.class));

        ProxyBeanDefinition proxyBeanDefinition = new ProxyBeanDefinition(HigherClass.class, advice, pointCut);
        beanFactory.registerBeanDefinition(HigherClass.class, proxyBeanDefinition);
        beanFactory.preInstantiateSingletons();
    }

    @Test
    @DisplayName("@Inject 필드의 빈이 잘 주입되었는지 확인")
    void inject() {
        HigherClass bean = beanFactory.getBean(HigherClass.class);

        assertThat(bean).isNotNull();
        assertThat(bean.getLowerClass()).isNotNull();
    }

}
