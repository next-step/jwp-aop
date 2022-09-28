package core.di.beans.factory.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.cglibproxy.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

class ProxyFactoryBeanTest {
    private final String name = "yang";
    private HelloTarget proxyInstance;

    @BeforeEach
    void setUp() {
        final Advice advice = new Advice() {
            @Override
            public void before(Method method, Class<?> clazz, Object[] args) {
                args[0] = "Mr." + args[0];
            }

            @Override
            public Object after(Method method, Class<?> clazz, Object[] args, Object result) {
                return result.toString().toUpperCase();
            }
        };
        final PointCut pointCut = (method, clazz, args) -> method.getName().startsWith("say");
        final Advisor advisor = new Advisor(advice, pointCut);
        final ProxyFactoryBean<HelloTarget> factoryBean = new ProxyFactoryBean<>(new HelloTarget(), advisor);
        proxyInstance = factoryBean.getObject();
    }

    @DisplayName("프록시 객체의 sayHello 메서드를 호출하면 AOP가 적용된 결과를 반환한다.")
    @Test
    void sayHello() {
        // when
        final String result = proxyInstance.sayHello(name);

        // then
        assertThat(result).isEqualTo("HELLO MR." + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayHi 메서드를 호출하면 AOP가 적용된 결과를 반환한다.")
    @Test
    void sayHi() {
        // when
        final String result = proxyInstance.sayHi(name);

        // then
        assertThat(result).isEqualTo("HI MR." + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayThankYou 메서드를 호출하면 AOP가 적용된 결과를 반환한다.")
    @Test
    void sayThankYou() {
        // when
        final String result = proxyInstance.sayThankYou(name);

        // then
        assertThat(result).isEqualTo("THANK YOU MR." + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 pingPong 메서드를 호출하면 AOP가 적용되지 않은 결과를 반환한다.")
    @Test
    void pingPong() {
        // when
        final String result = proxyInstance.pingPong(name);

        // then
        assertThat(result).isEqualTo("Pong " + name);
    }
}
