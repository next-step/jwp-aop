package core.aop;

import static org.assertj.core.api.Assertions.assertThat;

import core.aop.example.UpperResultStringAdvice;
import core.aop.pointcut.MatchMethodPointcut;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.cglib.HelloTarget;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
class ProxyFactoryBeanTest {

    @DisplayName("ProxyFactoryBean에 target, pointcut, advice 적용 테스트")
    @Test
    void upperProxyTest() throws Exception {
        HelloTarget target = new HelloTarget();
        MatchMethodPointcut pointcut = (m, targetClass, args) -> m.getName().startsWith("say");
        MethodInterceptor advice = new UpperResultStringAdvice(pointcut);

        ProxyFactoryBean pfb = new ProxyFactoryBean(target);
        pfb.addAdvice(advice);

        HelloTarget targetProxy = (HelloTarget) pfb.getObject();

        assertThat(target.sayHi("schulz")).isEqualTo("Hi schulz");
        assertThat(target.sayHello("schulz")).isEqualTo("Hello schulz");
        assertThat(target.sayThankYou("schulz")).isEqualTo("Thank You schulz");
        assertThat(target.pingpong("schulz")).isEqualTo("Pong schulz");

        assertThat(targetProxy.sayHi("schulz")).isEqualTo("HI SCHULZ");
        assertThat(targetProxy.sayHello("schulz")).isEqualTo("HELLO SCHULZ");
        assertThat(targetProxy.sayThankYou("schulz")).isEqualTo("THANK YOU SCHULZ");
        assertThat(targetProxy.pingpong("schulz")).isEqualTo("Pong schulz");
    }
}
