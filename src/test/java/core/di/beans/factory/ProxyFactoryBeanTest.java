package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.MethodMatcher;
import core.di.beans.factory.aop.PointCut;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * memo: CglibProxyTest에서 하지 못한 내용들 여기서 해결.
 */
@DisplayName("ProxyFactoryBean 동작 테스트")
class ProxyFactoryBeanTest {

    private ProxyFactoryBean proxyFactoryBean;

    @BeforeEach
    void setUp() {
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
    }

    @DisplayName("HelloTarget 어드바이스")
    @Test
    void advice() throws Exception {

        // given
        final String name = "hyeyoom";
        proxyFactoryBean.setAdvice(new UpperCaseAdvice());
        final HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        final String result = helloTarget.sayHello(name);

        // then
        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("HelloTarget 포인트컷")
    @Test
    void pointcut() throws Exception {

        // given
        final String name = "hyeyoom";
        proxyFactoryBean.setAdvice(new UpperCaseAdvice());
        proxyFactoryBean.setPointCut(new StartWithSayPointCut());
        final HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        final String result1 = helloTarget.sayHello(name);
        final String result2 = helloTarget.pingpong(name);

        // then
        assertThat(result1).isEqualTo("HELLO " + name.toUpperCase());
        assertThat(result2).isEqualTo("Pong " + name);
    }

    // HelloCglibInterceptor
    public static class UpperCaseAdvice implements Advice {
        @Override
        public Object invoke(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            final String returnValue = (String) methodProxy.invokeSuper(obj, args);
            return returnValue.toUpperCase();
        }
    }

    public static class StartWithSayPointCut implements PointCut {

        private final MethodMatcher methodMatcher = new StartWithSayMethodMatcher();

        @Override
        public MethodMatcher getMethodMatcher() {
            return methodMatcher;
        }
    }

    public static class StartWithSayMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getName().startsWith("say");
        }
    }
}